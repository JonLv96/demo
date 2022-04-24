package pers.kksg.demo.design.pattern.singleton;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/03/23/23:14
 * @Description: 双检索（diuble-checked locking）
 *
 * 是否 Lazy 初始化：是
 *
 * 是否多线程安全：是
 *
 * 实现难度：较复杂
 *
 * 描述：这种方式采用双锁机制，安全且在多线程情况下能保持高性能。
 * getInstance() 的性能对应用程序很关键。
 */
public class DCLSingleton {
    /**
     * 使用volatile关键字可以防止JVM指令重排序优化，保证线程间的可见性和有序性
     */
    private volatile static DCLSingleton singleton;

    private DCLSingleton() {
    }

    public static DCLSingleton getInstance() {
        //第一层判断单例模式只会创建一个对象，若已有对象即无需去竞争锁
        if (null == singleton) {
            synchronized (DCLSingleton.class) {
                //第二层判断是为了保证同步，防止实例化多次对象
                if (null == singleton) {
                    singleton = new DCLSingleton();
                }
            }
        }
        return singleton;
    }
}
