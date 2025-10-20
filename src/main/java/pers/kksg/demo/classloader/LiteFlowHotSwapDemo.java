package pers.kksg.demo.classloader;

import java.util.Scanner;

/**
 * LiteFlow风格热加载演示程序
 *
 * 演示LiteFlow热加载的核心特性：
 * 1. 配置文件监听和自动重载
 * 2. 流程定义解析和缓存
 * 3. 节点组件动态注册
 * 4. 执行链热重建
 * 5. 版本控制和状态管理
 */
public class LiteFlowHotSwapDemo {

    public static void main(String[] args) throws Exception {
        System.out.println("=== LiteFlow风格热加载演示 ===");
        System.out.println("基于LiteFlow核心热加载机制的简化实现\n");

        // 配置文件路径
        String configPath = System.getProperty("user.dir") + "/liteflow-config.xml";
        System.out.println("配置文件路径: " + configPath);

        // 创建LiteFlow风格的热加载管理器
        LiteFlowStyleHotSwap liteFlowHotSwap = new LiteFlowStyleHotSwap(configPath);

        // 启动热加载监控
        liteFlowHotSwap.startHotSwap();

        // 显示初始状态
        System.out.println("\n--- 初始状态 ---");
        liteFlowHotSwap.printStatus();

        // 执行流程测试
        System.out.println("\n--- 流程执行测试 ---");
        liteFlowHotSwap.executeFlow("demoChain");

        // 交互式演示
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== 交互式演示 ===");
        System.out.println("LiteFlow热加载特性:");
        System.out.println("1. 修改配置文件 " + configPath);
        System.out.println("2. 重新编译节点组件类");
        System.out.println("3. 观察热加载自动生效");
        System.out.println("\n按 Enter 键查看当前状态，输入 'quit' 退出:");

        while (true) {
            String input = scanner.nextLine();
            if ("quit".equalsIgnoreCase(input)) {
                break;
            }

            System.out.println("\n--- 当前状态 ---");
            liteFlowHotSwap.printStatus();

            System.out.println("\n--- 执行流程 ---");
            liteFlowHotSwap.executeFlow("demoChain");

            System.out.println("\n继续按 Enter 查看状态，输入 'quit' 退出:");
        }

        // 停止热加载监控
        liteFlowHotSwap.stopHotSwap();
        scanner.close();

        System.out.println("\n=== 演示结束 ===");
    }

    /**
     * 演示手动修改配置的效果
     */
    public static void demonstrateConfigModification() throws Exception {
        System.out.println("=== 演示配置文件修改效果 ===");

        String configPath = System.getProperty("user.dir") + "/liteflow-config.xml";
        LiteFlowStyleHotSwap liteFlowHotSwap = new LiteFlowStyleHotSwap(configPath);

        liteFlowHotSwap.startHotSwap();

        System.out.println("初始执行:");
        liteFlowHotSwap.executeFlow("demoChain");

        System.out.println("\n现在修改配置文件...（在实际使用中）");
        System.out.println("1. 修改 " + configPath);
        System.out.println("2. LiteFlow会自动检测文件变化");
        System.out.println("3. 重新解析流程定义");
        System.out.println("4. 重建执行链");
        System.out.println("5. 热更新生效");

        liteFlowHotSwap.stopHotSwap();
    }

    /**
     * 演示节点组件的热更新
     */
    public static void demonstrateNodeHotSwap() throws Exception {
        System.out.println("=== 演示节点组件热更新 ===");

        String configPath = System.getProperty("user.dir") + "/liteflow-config.xml";
        LiteFlowStyleHotSwap liteFlowHotSwap = new LiteFlowStyleHotSwap(configPath);

        liteFlowHotSwap.startHotSwap();

        System.out.println("执行原始节点:");
        liteFlowHotSwap.executeFlow("demoChain");

        System.out.println("\n节点组件热更新机制:");
        System.out.println("1. 使用自定义NodeClassLoader加载节点类");
        System.out.println("2. 每次热更新创建新的类加载器实例");
        System.out.println("3. 重新实例化节点组件");
        System.out.println("4. 保持配置和属性的连续性");
        System.out.println("5. 无需重启应用即可生效");

        liteFlowHotSwap.stopHotSwap();
    }
}