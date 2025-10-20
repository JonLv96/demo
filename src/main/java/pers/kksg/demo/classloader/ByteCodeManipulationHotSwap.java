package pers.kksg.demo.classloader;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 基于字节码操作的热更新实现
 * 使用ASM或Javassist等字节码操作库在运行时修改类行为
 */
public class ByteCodeManipulationHotSwap {

    // JDK8兼容的函数式接口定义
    public interface Validator {
        boolean validate(String input);
    }

    public interface Formatter {
        String format(String input);
    }

    private final AtomicLong modificationCounter = new AtomicLong(0);

    /**
     * 简单的类文件转换器实现
     */
    public static class SimpleClassTransformer implements ClassFileTransformer {
        private final String targetClassName;
        private final byte[] newBytecode;

        public SimpleClassTransformer(String targetClassName, byte[] newBytecode) {
            this.targetClassName = targetClassName.replace('.', '/');
            this.newBytecode = newBytecode;
        }

        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                               ProtectionDomain protectionDomain, byte[] classfileBuffer)
                               throws IllegalClassFormatException {

            if (targetClassName.equals(className)) {
                System.out.println("字节码转换器正在处理类: " + className);
                return newBytecode;
            }

            return null; // 返回null表示不修改
        }
    }

    /**
     * 动态方法调用处理器
     */
    public static class DynamicMethodInvoker {
        private final Object target;
        private final String methodName;

        public DynamicMethodInvoker(Object target, String methodName) {
            this.target = target;
            this.methodName = methodName;
        }

        public Object invoke(Object... args) throws Exception {
            Class<?>[] paramTypes = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                paramTypes[i] = args[i].getClass();
            }

            try {
                Method method = target.getClass().getMethod(methodName, paramTypes);
                return method.invoke(target, args);
            } catch (NoSuchMethodException e) {
                // 尝试使用参数类型的父类或接口
                for (Method method : target.getClass().getMethods()) {
                    if (method.getName().equals(methodName) &&
                        method.getParameterCount() == args.length) {
                        boolean match = true;
                        Class<?>[] methodParamTypes = method.getParameterTypes();
                        for (int i = 0; i < args.length; i++) {
                            if (!isCompatible(methodParamTypes[i], args[i].getClass())) {
                                match = false;
                                break;
                            }
                        }
                        if (match) {
                            return method.invoke(target, args);
                        }
                    }
                }
                throw e;
            }
        }

        // JDK8兼容的类型检查方法
        private boolean isCompatible(Class<?> targetType, Class<?> argType) {
            return targetType.isAssignableFrom(argType);
        }
    }

    /**
     * 热更新装饰器模式实现
     */
    public static class HotSwapDecorator {
        private final Object original;
        private Object enhanced;

        public HotSwapDecorator(Object original) {
            this.original = original;
            this.enhanced = original;
        }

        public void enhanceWith(Object newEnhancement) {
            this.enhanced = newEnhancement;
        }

        public Object invoke(String methodName, Object... args) throws Exception {
            DynamicMethodInvoker invoker = new DynamicMethodInvoker(enhanced, methodName);
            return invoker.invoke(args);
        }

        public void reset() {
            this.enhanced = original;
        }

        public Object getCurrent() {
            return enhanced;
        }
    }

    /**
     * 版本化类管理器
     */
    public static class VersionedClassManager {
        private final String className;
        private final Map<Long, Class<?>> versionedClasses = new ConcurrentHashMap<>();
        private final Map<Long, Object> versionedInstances = new ConcurrentHashMap<>();
        private volatile long currentVersion = 0;

        public VersionedClassManager(String className) {
            this.className = className;
        }

        public void registerNewVersion(byte[] bytecode, String classPath) throws Exception {
            long newVersion = currentVersion + 1;

            // 创建新的类加载器
            ClassLoader tempLoader = new ClassLoader() {
                @Override
                protected Class<?> findClass(String name) throws ClassNotFoundException {
                    if (name.equals(className)) {
                        return defineClass(name, bytecode, 0, bytecode.length);
                    }
                    return super.findClass(name);
                }
            };

            Class<?> newClass = tempLoader.loadClass(className);
            Object newInstance = newClass.newInstance();

            versionedClasses.put(newVersion, newClass);
            versionedInstances.put(newVersion, newInstance);
            currentVersion = newVersion;

            System.out.println("注册新版本: " + className + " v" + newVersion);
        }

        public Object getCurrentInstance() {
            return versionedInstances.get(currentVersion);
        }

        public Class<?> getCurrentClass() {
            return versionedClasses.get(currentVersion);
        }

        public long getCurrentVersion() {
            return currentVersion;
        }

        public Object switchToVersion(long version) {
            if (versionedInstances.containsKey(version)) {
                currentVersion = version;
                System.out.println("切换到版本: " + className + " v" + version);
                return versionedInstances.get(version);
            }
            throw new RuntimeException("版本不存在: " + version);
        }

        public void cleanupOldVersions(long keepVersions) {
            long threshold = currentVersion - keepVersions;

            // JDK8兼容的删除方式
            java.util.Iterator<java.util.Map.Entry<Long, Class<?>>> classIterator = versionedClasses.entrySet().iterator();
            while (classIterator.hasNext()) {
                java.util.Map.Entry<Long, Class<?>> entry = classIterator.next();
                if (entry.getKey() < threshold) {
                    classIterator.remove();
                }
            }

            java.util.Iterator<java.util.Map.Entry<Long, Object>> instanceIterator = versionedInstances.entrySet().iterator();
            while (instanceIterator.hasNext()) {
                java.util.Map.Entry<Long, Object> entry = instanceIterator.next();
                if (entry.getKey() < threshold) {
                    instanceIterator.remove();
                }
            }
        }
    }

    /**
     * 动态行为注入
     */
    public static class DynamicBehaviorInjector {
        private final Map<String, Object> behaviors = new ConcurrentHashMap<>();

        public void injectBehavior(String key, Object behavior) {
            behaviors.put(key, behavior);
            System.out.println("注入行为: " + key + " -> " + behavior.getClass().getSimpleName());
        }

        public Object getBehavior(String key) {
            return behaviors.get(key);
        }

        public void removeBehavior(String key) {
            behaviors.remove(key);
            System.out.println("移除行为: " + key);
        }

        public boolean hasBehavior(String key) {
            return behaviors.containsKey(key);
        }
    }

    /**
     * 演示字节码操作热更新
     */
    public static void demonstrateByteCodeHotSwap() throws Exception {
        System.out.println("=== 字节码操作热更新演示 ===");

        // 1. 创建原始服务
        HotSwapDemoService originalService = new HotSwapDemoService();
        System.out.println("原始服务版本: " + originalService.getVersion());

        // 2. 使用装饰器模式
        HotSwapDecorator decorator = new HotSwapDecorator(originalService);
        System.out.println("装饰器调用结果: " +
                          decorator.invoke("processMessage", "Hello Decorator"));

        // 3. 创建增强版本
        HotSwapDemoService enhancedService = new HotSwapDemoService() {
            @Override
            public String getVersion() {
                return "2.0-Enhanced";
            }

            @Override
            public String processMessage(String message) {
                return "[增强版] " + super.processMessage(message);
            }
        };

        // 4. 热更新装饰器
        decorator.enhanceWith(enhancedService);
        System.out.println("热更新后版本: " +
                          decorator.invoke("getVersion"));
        System.out.println("热更新后调用: " +
                          decorator.invoke("processMessage", "Hello Enhanced"));

        // 5. 版本化管理
        VersionedClassManager versionManager = new VersionedClassManager(
            "pers.kksg.demo.classloader.HotSwapDemoService"
        );

        // 6. 动态行为注入
        DynamicBehaviorInjector behaviorInjector = new DynamicBehaviorInjector();
        behaviorInjector.injectBehavior("validator", new Validator() {
            @Override
            public boolean validate(String input) {
                return input != null && !input.trim().isEmpty();
            }
        });

        behaviorInjector.injectBehavior("formatter", new Formatter() {
            @Override
            public String format(String input) {
                return "[" + System.currentTimeMillis() + "] " + input;
            }
        });

        // JDK8兼容的函数式接口调用
        Object validator = behaviorInjector.getBehavior("validator");
        if (validator instanceof Validator) {
            System.out.println("验证器行为: " + ((Validator) validator).validate("test"));
        } else {
            System.out.println("验证器类型不匹配");
        }
    }
}