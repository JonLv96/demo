package pers.kksg.demo.classloader;

/**
 * 用于演示热更新的服务类
 */
public class HotSwapDemoService {

    private String version = "1.0";

    public String getVersion() {
        return version;
    }

    public String processMessage(String message) {
        return "处理消息 (v" + version + "): " + message.toUpperCase();
    }

    public int calculate(int a, int b) {
        // 这个方法会在后续版本中被修改以演示热更新
        return a + b;
    }

    public void printInfo() {
        System.out.println("HotSwapDemoService - 版本: " + version);
        System.out.println("类加载器: " + this.getClass().getClassLoader());
        System.out.println("内存地址: " + System.identityHashCode(this));
    }
}