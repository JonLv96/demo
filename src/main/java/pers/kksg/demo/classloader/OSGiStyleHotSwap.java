package pers.kksg.demo.classloader;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.HashMap;
import java.util.ServiceLoader;

/**
 * OSGi风格的模块化热更新实现
 * 通过模块化的类加载器实现热插拔
 */
public class OSGiStyleHotSwap {

    private final Map<String, ModuleClassLoader> modules = new ConcurrentHashMap<>();
    private final Map<String, Object> moduleInstances = new ConcurrentHashMap<>();
    private final Map<String, Long> moduleVersions = new ConcurrentHashMap<>();

    /**
     * 模块化类加载器
     */
    private static class ModuleClassLoader extends ClassLoader {
        private final String moduleName;
        private final Map<String, Class<?>> loadedClasses = new HashMap<>();
        private final String classPath;

        public ModuleClassLoader(String moduleName, String classPath) {
            super(ModuleClassLoader.class.getClassLoader());
            this.moduleName = moduleName;
            this.classPath = classPath;
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            // 只加载属于当前模块的类
            if (!name.contains(moduleName)) {
                return super.findClass(name);
            }

            Class<?> clazz = loadedClasses.get(name);
            if (clazz != null) {
                return clazz;
            }

            try {
                byte[] classData = loadClassData(name);
                if (classData != null) {
                    clazz = defineClass(name, classData, 0, classData.length);
                    loadedClasses.put(name, clazz);
                    return clazz;
                }
            } catch (Exception e) {
                throw new ClassNotFoundException("Failed to load class: " + name, e);
            }

            throw new ClassNotFoundException("Class not found: " + name);
        }

        private byte[] loadClassData(String className) throws Exception {
            String classFile = className.replace('.', '/') + ".class";
            String fullPath = classPath + "/" + classFile;

            try (java.io.FileInputStream fis = new java.io.FileInputStream(fullPath);
                 java.io.ByteArrayOutputStream bos = new java.io.ByteArrayOutputStream()) {

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    bos.write(buffer, 0, bytesRead);
                }
                return bos.toByteArray();
            }
        }

        public void unloadClass(String className) {
            loadedClasses.remove(className);
        }

        public void clearCache() {
            loadedClasses.clear();
        }
    }

    /**
     * 注册模块
     */
    public void registerModule(String moduleName, String className, String classPath) throws Exception {
        System.out.println("注册模块: " + moduleName + ", 主类: " + className);

        ModuleClassLoader classLoader = new ModuleClassLoader(moduleName, classPath);
        modules.put(moduleName, classLoader);

        Class<?> moduleClass = classLoader.loadClass(className);
        Object moduleInstance = moduleClass.newInstance();

        moduleInstances.put(moduleName, moduleInstance);
        moduleVersions.put(moduleName, System.currentTimeMillis());

        System.out.println("模块注册成功: " + moduleName);
    }

    /**
     * 热更新模块
     */
    public boolean hotSwapModule(String moduleName) {
        try {
            ModuleClassLoader oldClassLoader = modules.get(moduleName);
            Object oldInstance = moduleInstances.get(moduleName);

            if (oldClassLoader == null) {
                System.err.println("模块不存在: " + moduleName);
                return false;
            }

            // 创建新的类加载器
            ModuleClassLoader newClassLoader = new ModuleClassLoader(
                moduleName,
                System.getProperty("user.dir") + "/target/classes"
            );

            // 获取模块主类名
            String className = "pers.kksg.demo.classloader." + moduleName + "Service";

            // 使用新的类加载器加载类
            Class<?> newModuleClass = newClassLoader.loadClass(className);
            Object newModuleInstance = newModuleClass.newInstance();

            // 替换旧的模块
            modules.put(moduleName, newClassLoader);
            moduleInstances.put(moduleName, newModuleInstance);
            moduleVersions.put(moduleName, System.currentTimeMillis());

            System.out.println("模块热更新成功: " + moduleName);
            return true;

        } catch (Exception e) {
            System.err.println("模块热更新失败: " + moduleName + ", " + e.getMessage());
            return false;
        }
    }

    /**
     * 获取模块实例
     */
    public Object getModule(String moduleName) {
        return moduleInstances.get(moduleName);
    }

    /**
     * 调用模块方法
     */
    public Object invokeModuleMethod(String moduleName, String methodName, Object... args) throws Exception {
        Object module = getModule(moduleName);
        if (module == null) {
            throw new RuntimeException("模块不存在: " + moduleName);
        }

        Class<?>[] paramTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            paramTypes[i] = args[i].getClass();
        }

        java.lang.reflect.Method method = module.getClass().getMethod(methodName, paramTypes);
        return method.invoke(module, args);
    }

    /**
     * 卸载模块
     */
    public void unloadModule(String moduleName) {
        ModuleClassLoader classLoader = modules.remove(moduleName);
        moduleInstances.remove(moduleName);
        moduleVersions.remove(moduleName);

        if (classLoader != null) {
            classLoader.clearCache();
        }

        System.out.println("模块已卸载: " + moduleName);
    }

    /**
     * 获取模块信息
     */
    public void printModuleInfo() {
        System.out.println("=== 模块信息 ===");
        for (Map.Entry<String, Object> entry : moduleInstances.entrySet()) {
            String moduleName = entry.getKey();
            Object instance = entry.getValue();
            Long version = moduleVersions.get(moduleName);

            System.out.println("模块: " + moduleName);
            System.out.println("  版本: " + version);
            System.out.println("  实例: " + instance.getClass().getName());
            System.out.println("  类加载器: " + instance.getClass().getClassLoader());
            System.out.println();
        }
    }
}