package pers.kksg.demo.classloader;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Scanner;

/**
 * 基于Instrumentation Agent的类热更新演示
 */
public class AgentHotSwapDemo {

    public static void main(String[] args) throws Exception {
        System.out.println("=== 基于Instrumentation Agent的类热更新演示 ===\n");

        // 创建初始服务实例
        HotSwapDemoService service = new HotSwapDemoService();

        System.out.println("1. 初始服务信息:");
        service.printInfo();
        System.out.println("版本: " + service.getVersion());
        System.out.println("计算结果: " + service.calculate(10, 20));

        System.out.println("\n2. 启动Agent热更新监控:");

        // 获取class文件路径
        String classPath = System.getProperty("user.dir") + "/target/classes/pers/kksg/demo/classloader";
        System.out.println("监控路径: " + classPath);

        // 启动自动热更新
        HotSwapAgent.startAutoHotSwap(classPath);

        System.out.println("\n3. 打印已加载类信息:");
        HotSwapAgent.printLoadedClassesInfo();

        System.out.println("\n=== 演示说明 ===");
        System.out.println("现在可以修改 HotSwapDemoService.java 源代码并重新编译，");
        System.out.println("Agent会自动检测class文件变化并热更新类。");
        System.out.println("\n当前运行的HotSwapDemoService实例会自动使用更新后的代码！");

        // 交互式演示
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n按 Enter 键查看当前服务状态，输入 'quit' 退出:");

        while (true) {
            String input = scanner.nextLine();
            if ("quit".equalsIgnoreCase(input)) {
                break;
            }

            System.out.println("\n--- 当前服务状态 ---");
            service.printInfo();
            System.out.println("版本: " + service.getVersion());
            System.out.println("计算结果: " + service.calculate(10, 20));
            System.out.println("消息处理: " + service.processMessage("Hello HotSwap"));

            System.out.println("\n继续按 Enter 查看状态，输入 'quit' 退出:");
        }

        // 停止监控
        HotSwapAgent.stopAutoHotSwap();
        scanner.close();
        System.out.println("\n=== 演示结束 ===");
    }

    /**
     * 演示手动热更新
     */
    public static void demonstrateManualHotSwap() throws Exception {
        System.out.println("=== 手动热更新演示 ===");

        // 创建服务实例
        HotSwapDemoService service = new HotSwapDemoService();
        System.out.println("初始版本: " + service.getVersion());

        // 获取class文件路径
        String classPath = System.getProperty("user.dir") + "/target/classes";
        String className = "pers.kksg.demo.classloader.HotSwapDemoService";
        String classFilePath = classPath + "/pers/kksg/demo/classloader/HotSwapDemoService.class";

        // 检查class文件是否存在
        File classFile = new File(classFilePath);
        if (!classFile.exists()) {
            System.err.println("Class文件不存在: " + classFilePath);
            System.err.println("请先编译项目生成class文件");
            return;
        }

        System.out.println("准备热更新类: " + className);
        System.out.println("Class文件路径: " + classFilePath);

        // 执行手动热更新
        boolean success = HotSwapAgent.hotSwapClass(className, classFilePath);
        if (success) {
            System.out.println("热更新成功！检查更新后的版本:");
            System.out.println("更新后版本: " + service.getVersion());
        } else {
            System.err.println("热更新失败");
        }
    }

    /**
     * 演示批量热更新
     */
    public static void demonstrateBatchHotSwap() {
        System.out.println("=== 批量热更新演示 ===");

        String classPath = System.getProperty("user.dir") + "/target/classes/pers/kksg/demo/classloader";

        java.util.Map<String, String> classesToUpdate = new java.util.HashMap<>();
        classesToUpdate.put("pers.kksg.demo.classloader.HotSwapDemoService",
                           classPath + "/HotSwapDemoService.class");
        classesToUpdate.put("pers.kksg.demo.classloader.HotSwapAgent",
                           classPath + "/HotSwapAgent.class");

        System.out.println("准备批量热更新以下类:");
        // JDK8兼容的遍历方式
        for (String className : classesToUpdate.keySet()) {
            System.out.println("  - " + className);
        }

        HotSwapAgent.hotSwapClasses(classesToUpdate);
    }
}