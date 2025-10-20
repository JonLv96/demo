package pers.kksg.demo.classloader;

/**
 * LiteFlow热加载功能测试
 * 非交互式测试，演示LiteFlow热加载的核心特性
 */
public class LiteFlowHotSwapTest {

    public static void main(String[] args) throws Exception {
        System.out.println("=== LiteFlow热加载功能测试 ===\n");

        // 配置文件路径
        String configPath = System.getProperty("user.dir") + "/liteflow-config.xml";

        // 创建LiteFlow热加载管理器
        LiteFlowStyleHotSwap liteFlowHotSwap = new LiteFlowStyleHotSwap(configPath);

        System.out.println("1. 启动热加载监控...");
        liteFlowHotSwap.startHotSwap();

        // 显示初始状态
        System.out.println("\n2. 初始状态:");
        liteFlowHotSwap.printStatus();

        // 执行流程测试
        System.out.println("\n3. 执行流程测试:");
        String result = liteFlowHotSwap.executeFlow("demoChain");
        System.out.println("执行结果: " + result);

        // 演示配置修改
        System.out.println("\n4. 模拟配置文件修改...");
        System.out.println("   在实际使用中，你可以修改 liteflow-config.xml 文件");
        System.out.println("   LiteFlow会自动检测变化并重新加载配置");

        // 再次执行流程
        System.out.println("\n5. 再次执行流程:");
        String result2 = liteFlowHotSwap.executeFlow("demoChain");
        System.out.println("执行结果: " + result2);

        // 停止监控
        System.out.println("\n6. 停止热加载监控");
        liteFlowHotSwap.stopHotSwap();

        System.out.println("\n=== LiteFlow热加载特性总结 ===");
        System.out.println("✓ 配置文件自动监听");
        System.out.println("✓ 流程定义动态解析");
        System.out.println("✓ 节点组件热注册");
        System.out.println("✓ 执行链实时重建");
        System.out.println("✓ 无需重启应用");

        System.out.println("\n=== 与LiteFlow官方实现对比 ===");
        System.out.println("• 本Demo: 简化版实现，演示核心原理");
        System.out.println("• LiteFlow: 企业级实现，功能更完善");
        System.out.println("• 相同点: 配置监听、流程缓存、节点管理");
        System.out.println("• 差异点: LiteFlow支持更多格式和高级特性");

        System.out.println("\n测试完成！");
    }
}