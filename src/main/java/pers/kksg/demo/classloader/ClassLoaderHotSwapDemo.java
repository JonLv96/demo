package pers.kksg.demo.classloader;

import java.lang.reflect.Method;

/**
 * 基于ClassLoader的类热更新演示
 */
public class ClassLoaderHotSwapDemo {

    public static void main(String[] args) throws Exception {
        System.out.println("=== 基于ClassLoader的类热更新演示 ===\n");

        // 获取class文件路径
        String classpath = System.getProperty("user.dir") + "/target/classes";
        System.out.println("Class文件路径: " + classpath);

        // 创建自定义类加载器
        HotSwapClassLoader classLoader = new HotSwapClassLoader(classpath);

        // 加载并使用HotSwapDemoService
        System.out.println("1. 初始加载HotSwapDemoService:");
        Class<?> serviceClass1 = classLoader.loadClass("pers.kksg.demo.classloader.HotSwapDemoService");
        Object service1 = serviceClass1.newInstance();

        // 调用方法
        Method processMessageMethod = serviceClass1.getMethod("processMessage", String.class);
        Method getVersionMethod = serviceClass1.getMethod("getVersion");
        Method printInfoMethod = serviceClass1.getMethod("printInfo");

        printInfoMethod.invoke(service1);
        System.out.println("版本: " + getVersionMethod.invoke(service1));
        System.out.println("处理结果: " + processMessageMethod.invoke(service1, "Hello World"));

        System.out.println("\n2. 创建新的类加载器实例并重新加载类:");
        // 创建新的类加载器实例来实现热更新
        HotSwapClassLoader newClassLoader = new HotSwapClassLoader(classpath);
        Class<?> serviceClass2 = newClassLoader.loadClass("pers.kksg.demo.classloader.HotSwapDemoService");
        Object service2 = serviceClass2.newInstance();

        printInfoMethod.invoke(service2);
        System.out.println("版本: " + getVersionMethod.invoke(service2));
        System.out.println("处理结果: " + processMessageMethod.invoke(service2, "Hello World"));

        System.out.println("\n3. 比较两个类是否相同:");
        System.out.println("service1的类: " + service1.getClass());
        System.out.println("service2的类: " + service2.getClass());
        System.out.println("两个类是否相同: " + service1.getClass().equals(service2.getClass()));
        System.out.println("两个类的类加载器是否相同: " +
                          service1.getClass().getClassLoader().equals(service2.getClass().getClassLoader()));

        // 演示热更新检测
        System.out.println("\n4. 演示热更新检测:");
        System.out.println("service1是否被修改: " + classLoader.isClassModified("pers.kksg.demo.classloader.HotSwapDemoService"));

        System.out.println("\n=== 演示完成 ===");
        System.out.println("\n注意: 要实现真正的热更新，需要:");
        System.out.println("1. 修改HotSwapDemoService.java源代码");
        System.out.println("2. 重新编译生成新的class文件");
        System.out.println("3. 使用新的类加载器加载更新的类");
    }

    /**
     * 演示如何在运行时动态加载和使用更新后的类
     */
    public static void demonstrateRuntimeHotSwap() throws Exception {
        String classpath = System.getProperty("user.dir") + "/target/classes";
        HotSwapClassLoader classLoader = new HotSwapClassLoader(classpath);

        while (true) {
            try {
                System.out.println("\n--- 检查并执行热更新 ---");

                // 每次都创建新的类加载器来加载最新版本的类
                Class<?> serviceClass = classLoader.loadClass("pers.kksg.demo.classloader.HotSwapDemoService");
                Object service = serviceClass.newInstance();

                Method printInfoMethod = serviceClass.getMethod("printInfo");
                Method getVersionMethod = serviceClass.getMethod("getVersion");

                printInfoMethod.invoke(service);
                System.out.println("当前版本: " + getVersionMethod.invoke(service));

                Thread.sleep(3000); // 每3秒检查一次

            } catch (Exception e) {
                System.err.println("执行出错: " + e.getMessage());
                Thread.sleep(5000);
            }
        }
    }
}