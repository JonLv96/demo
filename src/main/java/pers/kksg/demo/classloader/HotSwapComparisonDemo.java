package pers.kksg.demo.classloader;

import java.util.Scanner;

/**
 * 热更新实现方式对比演示
 */
public class HotSwapComparisonDemo {

    public static void main(String[] args) throws Exception {
        System.out.println("=== Java类热更新实现方式对比演示 ===\n");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n请选择要演示的热更新方式:");
            System.out.println("1. 基于ClassLoader的热更新");
            System.out.println("2. 基于Instrumentation Agent的热更新");
            System.out.println("3. OSGi风格的模块化热更新");
            System.out.println("4. 基于字节码操作的热更新");
            System.out.println("5. 全部演示");
            System.out.println("0. 退出");

            System.out.print("\n请输入选择: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    demonstrateClassLoaderHotSwap();
                    break;
                case "2":
                    demonstrateAgentHotSwap();
                    break;
                case "3":
                    demonstrateOSGiStyleHotSwap();
                    break;
                case "4":
                    demonstrateByteCodeHotSwap();
                    break;
                case "5":
                    demonstrateAll();
                    break;
                case "0":
                    System.out.println("退出演示");
                    scanner.close();
                    return;
                default:
                    System.out.println("无效选择，请重新输入");
            }

            System.out.println("\n按 Enter 继续...");
            scanner.nextLine();
        }
    }

    private static void demonstrateClassLoaderHotSwap() {
        System.out.println("\n=== 1. 基于ClassLoader的热更新 ===");
        System.out.println("原理: 通过创建新的类加载器实例加载同名类的不同版本");
        System.out.println("优点: 实现简单，不依赖特殊API");
        System.out.println("缺点: 需要管理类加载器生命周期，可能出现类加载器内存泄漏");

        try {
            ClassLoaderHotSwapDemo.demonstrateRuntimeHotSwap();
        } catch (Exception e) {
            System.out.println("演示出错: " + e.getMessage());
        }
    }

    private static void demonstrateAgentHotSwap() {
        System.out.println("\n=== 2. 基于Instrumentation Agent的热更新 ===");
        System.out.println("原理: 使用Java Instrumentation API在运行时重新定义类");
        System.out.println("优点: 可以直接修改已加载的类，无需创建新实例");
        System.out.println("缺点: 需要Java Agent支持，配置相对复杂");

        try {
            AgentHotSwapDemo.demonstrateManualHotSwap();
        } catch (Exception e) {
            System.out.println("演示出错: " + e.getMessage());
        }
    }

    private static void demonstrateOSGiStyleHotSwap() {
        System.out.println("\n=== 3. OSGi风格的模块化热更新 ===");
        System.out.println("原理: 将功能模块化，每个模块使用独立的类加载器");
        System.out.println("优点: 支持模块级别的热插拔，隔离性好");
        System.out.println("缺点: 架构复杂，需要额外的模块管理机制");

        try {
            OSGiStyleHotSwap osgiHotSwap = new OSGiStyleHotSwap();

            // 注册演示模块
            String classPath = System.getProperty("user.dir") + "/target/classes";
            osgiHotSwap.registerModule("HotSwapDemo", "pers.kksg.demo.classloader.HotSwapDemoService", classPath);

            // 调用模块方法
            Object result = osgiHotSwap.invokeModuleMethod("HotSwapDemo", "getVersion");
            System.out.println("模块版本: " + result);

            result = osgiHotSwap.invokeModuleMethod("HotSwapDemo", "processMessage", "Hello OSGi");
            System.out.println("模块处理结果: " + result);

            // 演示热更新
            System.out.println("执行模块热更新...");
            osgiHotSwap.hotSwapModule("HotSwapDemo");

            result = osgiHotSwap.invokeModuleMethod("HotSwapDemo", "getVersion");
            System.out.println("热更新后版本: " + result);

        } catch (Exception e) {
            System.out.println("演示出错: " + e.getMessage());
        }
    }

    private static void demonstrateByteCodeHotSwap() {
        System.out.println("\n=== 4. 基于字节码操作的热更新 ===");
        System.out.println("原理: 在运行时修改类字节码，动态改变方法行为");
        System.out.println("优点: 灵活性高，可以实现细粒度的行为修改");
        System.out.println("缺点: 需要字节码操作库，实现复杂");

        try {
            ByteCodeManipulationHotSwap.demonstrateByteCodeHotSwap();
        } catch (Exception e) {
            System.out.println("演示出错: " + e.getMessage());
        }
    }

    private static void demonstrateAll() {
        System.out.println("\n=== 全部热更新方式对比 ===");

        System.out.println("\n1. 实现复杂度对比:");
        System.out.println("   ClassLoader方式: ★★☆☆☆ (相对简单)");
        System.out.println("   Instrumentation方式: ★★★☆☆ (中等复杂)");
        System.out.println("   OSGi方式: ★★★★★ (最复杂)");
        System.out.println("   字节码操作方式: ★★★★☆ (较复杂)");

        System.out.println("\n2. 性能影响对比:");
        System.out.println("   ClassLoader方式: 中等 (需要创建新类加载器)");
        System.out.println("   Instrumentation方式: 较小 (直接重定义类)");
        System.out.println("   OSGi方式: 较大 (模块管理开销)");
        System.out.println("   字节码操作方式: 较小 (仅修改特定字节码)");

        System.out.println("\n3. 适用场景对比:");
        System.out.println("   ClassLoader方式: 简单的热更新需求，快速原型");
        System.out.println("   Instrumentation方式: 生产环境，需要稳定性");
        System.out.println("   OSGi方式: 大型应用，模块化架构");
        System.out.println("   字节码操作方式: AOP，动态代理，调试工具");

        System.out.println("\n4. 主流框架使用:");
        System.out.println("   ClassLoader方式: Tomcat热部署，OSGi");
        System.out.println("   Instrumentation方式: Arthas，BTrace，Java调试器");
        System.out.println("   OSGi方式: Eclipse Equinox，Apache Felix");
        System.out.println("   字节码操作方式: Spring AOP，Hibernate，Mockito");

        System.out.println("\n5. 最佳实践建议:");
        System.out.println("   - 简单应用: 使用ClassLoader方式");
        System.out.println("   - 生产环境: 使用Instrumentation Agent");
        System.out.println("   - 模块化系统: 考虑OSGi或模块化ClassLoader");
        System.out.println("   - 需要AOP: 使用字节码操作 + 动态代理");
        System.out.println("   - 混合使用: 根据不同场景选择合适的方式");
    }
}