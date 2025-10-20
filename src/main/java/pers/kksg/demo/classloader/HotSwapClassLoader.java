package pers.kksg.demo.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义热更新类加载器
 * 通过打破双亲委派机制实现类的热更新
 */
public class HotSwapClassLoader extends ClassLoader {

    private final Map<String, Class<?>> loadedClasses = new HashMap<>();
    private final String classpath;

    public HotSwapClassLoader(String classpath) {
        this.classpath = classpath;
    }

    public HotSwapClassLoader(String classpath, ClassLoader parent) {
        super(parent);
        this.classpath = classpath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // 检查是否已加载该类
        Class<?> clazz = loadedClasses.get(name);
        if (clazz != null) {
            return clazz;
        }

        try {
            // 将类名转换为文件路径
            String classFile = name.replace('.', '/') + ".class";
            String fullPath = classpath + File.separator + classFile;

            // 读取class文件
            byte[] classData = loadClassData(fullPath);
            if (classData == null) {
                throw new ClassNotFoundException("Class " + name + " not found");
            }

            // 定义类
            clazz = defineClass(name, classData, 0, classData.length);
            loadedClasses.put(name, clazz);
            return clazz;

        } catch (IOException e) {
            throw new ClassNotFoundException("Failed to load class " + name, e);
        }
    }

    private byte[] loadClassData(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }

        try (FileInputStream fis = new FileInputStream(file);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }

            return bos.toByteArray();
        }
    }

    /**
     * 热更新指定的类
     * @param className 类的全限定名
     * @return 新加载的Class对象
     */
    public Class<?> hotSwapClass(String className) throws Exception {
        // 从缓存中移除旧的类定义
        loadedClasses.remove(className);

        // 重新加载类
        return findClass(className);
    }

    /**
     * 检查class文件是否被修改
     */
    public boolean isClassModified(String className) {
        String classFile = className.replace('.', '/') + ".class";
        String fullPath = classpath + File.separator + classFile;
        File file = new File(fullPath);

        if (!file.exists()) {
            return false;
        }

        // 这里可以添加更复杂的修改检测逻辑
        // 比如记录文件的最后修改时间
        return true;
    }

    /**
     * 监控class文件变化并自动热更新
     */
    public void startAutoHotSwap() throws Exception {
        Path path = Paths.get(classpath);
        WatchService watchService = FileSystems.getDefault().newWatchService();

        path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

        new Thread(new Runnable() {
            @Override
            public void run() {
            try {
                while (true) {
                    WatchKey key = watchService.take();

                    for (WatchEvent<?> event : key.pollEvents()) {
                        if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                            String fileName = event.context().toString();
                            if (fileName.endsWith(".class")) {
                                String className = fileName.replace(".class", "").replace('/', '.');
                                System.out.println("检测到类文件修改: " + className);

                                try {
                                    hotSwapClass(className);
                                    System.out.println("热更新成功: " + className);
                                } catch (Exception e) {
                                    System.err.println("热更新失败: " + className + ", " + e.getMessage());
                                }
                            }
                        }
                    }

                    key.reset();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        }).start();

        System.out.println("自动热更新监控已启动，监控路径: " + classpath);
    }
}