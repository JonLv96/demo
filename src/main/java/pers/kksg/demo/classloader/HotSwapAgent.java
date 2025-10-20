package pers.kksg.demo.classloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.nio.file.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Java Agent实现类热更新
 * 使用Instrumentation API在运行时重新定义类
 */
public class HotSwapAgent {

    private static final Map<String, Long> lastModifiedCache = new ConcurrentHashMap<>();
    private static final Map<String, byte[]> classBytesCache = new ConcurrentHashMap<>();
    private static Instrumentation instrumentation;
    private static Thread watchThread;
    private static boolean isWatching = false;

    /**
     * Agent入口方法 - JVM启动时调用
     */
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("HotSwap Agent 启动 (premain)");
        initialize(inst);
    }

    /**
     * Agent入口方法 - 运行时attach调用
     */
    public static void agentmain(String agentArgs, Instrumentation inst) {
        System.out.println("HotSwap Agent 启动 (agentmain)");
        initialize(inst);
    }

    private static void initialize(Instrumentation inst) {
        instrumentation = inst;
        System.out.println("Instrumentation 初始化完成");
        System.out.println("是否支持类重定义: " + inst.isRedefineClassesSupported());
        System.out.println("是否支持类重转换: " + inst.isRetransformClassesSupported());
    }

    /**
     * 热更新指定的类
     */
    public static boolean hotSwapClass(String className, String classFilePath) {
        if (instrumentation == null) {
            System.err.println("Instrumentation 未初始化");
            return false;
        }

        try {
            // 读取新的class文件
            byte[] newClassBytes = readClassFile(classFilePath);
            if (newClassBytes == null) {
                System.err.println("无法读取class文件: " + classFilePath);
                return false;
            }

            // 查找要重新定义的类
            Class<?> targetClass = findLoadedClass(className);
            if (targetClass == null) {
                System.err.println("未找到已加载的类: " + className);
                return false;
            }

            // 创建新的类定义
            ClassDefinition newClassDef = new ClassDefinition(targetClass, newClassBytes);

            // 重新定义类
            instrumentation.redefineClasses(newClassDef);

            System.out.println("成功热更新类: " + className);
            return true;

        } catch (Exception e) {
            System.err.println("热更新失败: " + className + ", 错误: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 查找已加载的类
     */
    private static Class<?> findLoadedClass(String className) {
        // 遍历所有已加载的类
        for (Class<?> clazz : instrumentation.getAllLoadedClasses()) {
            if (clazz.getName().equals(className)) {
                return clazz;
            }
        }
        return null;
    }

    /**
     * 读取class文件内容
     */
    private static byte[] readClassFile(String filePath) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        try {
            // JDK8兼容的文件读取方式
            java.io.ByteArrayOutputStream bos = new java.io.ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            return bos.toByteArray();
        } finally {
            fis.close();
        }
    }

    /**
     * 启动自动热更新监控
     */
    public static void startAutoHotSwap(String classPath) {
        if (isWatching) {
            System.out.println("自动热更新监控已在运行");
            return;
        }

        isWatching = true;
        System.out.println("启动自动热更新监控，监控路径: " + classPath);

        watchThread = new Thread(new Runnable() {
            @Override
            public void run() {
            try {
                Path path = Paths.get(classPath);
                WatchService watchService = FileSystems.getDefault().newWatchService();
                path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

                while (isWatching) {
                    WatchKey key = watchService.take();

                    for (WatchEvent<?> event : key.pollEvents()) {
                        if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                            handleFileModification(event, classPath);
                        }
                    }

                    key.reset();
                }
            } catch (Exception e) {
                System.err.println("自动热更新监控出错: " + e.getMessage());
            }
        }
        });

        watchThread.setDaemon(true);
        watchThread.start();
    }

    /**
     * 停止自动热更新监控
     */
    public static void stopAutoHotSwap() {
        isWatching = false;
        if (watchThread != null) {
            watchThread.interrupt();
        }
        System.out.println("自动热更新监控已停止");
    }

    /**
     * 处理文件修改事件
     */
    private static void handleFileModification(WatchEvent<?> event, String classPath) {
        String fileName = event.context().toString();
        if (!fileName.endsWith(".class")) {
            return;
        }

        try {
            // 将文件名转换为类名
            String className = fileName.replace(".class", "").replace('/', '.').replace('\\', '.');
            String fullPath = classPath + File.separator + fileName;

            File file = new File(fullPath);
            long lastModified = file.lastModified();

            // 检查文件是否真的被修改了（避免重复事件）
            Long cachedLastModified = lastModifiedCache.get(className);
            if (cachedLastModified != null && cachedLastModified.equals(lastModified)) {
                return;
            }

            System.out.println("检测到类文件修改: " + className);

            // 执行热更新
            if (hotSwapClass(className, fullPath)) {
                lastModifiedCache.put(className, lastModified);
                System.out.println("自动热更新成功: " + className);
            } else {
                System.err.println("自动热更新失败: " + className);
            }

        } catch (Exception e) {
            System.err.println("处理文件修改时出错: " + fileName + ", " + e.getMessage());
        }
    }

    /**
     * 批量热更新多个类
     */
    public static void hotSwapClasses(Map<String, String> classNameToFilePath) {
        int successCount = 0;
        int failCount = 0;

        for (Map.Entry<String, String> entry : classNameToFilePath.entrySet()) {
            String className = entry.getKey();
            String filePath = entry.getValue();

            if (hotSwapClass(className, filePath)) {
                successCount++;
            } else {
                failCount++;
            }
        }

        System.out.println("批量热更新完成 - 成功: " + successCount + ", 失败: " + failCount);
    }

    /**
     * 获取已加载类的信息
     */
    public static void printLoadedClassesInfo() {
        if (instrumentation == null) {
            System.out.println("Instrumentation 未初始化");
            return;
        }

        System.out.println("=== 已加载的类信息 ===");
        System.out.println("总类数: " + instrumentation.getAllLoadedClasses().length);

        // 统计类加载器信息 - JDK8兼容写法
        Map<ClassLoader, Integer> classLoaderStats = new java.util.HashMap<>();
        for (Class<?> clazz : instrumentation.getAllLoadedClasses()) {
            ClassLoader loader = clazz.getClassLoader();
            Integer count = classLoaderStats.get(loader);
            if (count == null) {
                classLoaderStats.put(loader, 1);
            } else {
                classLoaderStats.put(loader, count + 1);
            }
        }

        System.out.println("\n类加载器统计:");
        for (Map.Entry<ClassLoader, Integer> entry : classLoaderStats.entrySet()) {
            ClassLoader loader = entry.getKey();
            Integer count = entry.getValue();
            System.out.println("  " + loader + ": " + count + " 个类");
        }
    }
}