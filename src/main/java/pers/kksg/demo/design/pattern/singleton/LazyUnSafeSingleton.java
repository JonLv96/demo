package pers.kksg.demo.design.pattern.singleton;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/03/23/21:42
 * @Description: 懒加载非线程安全
 *
 * 是否 Lazy 初始化：是
 *
 * 是否多线程安全：否
 *
 * 实现难度：易
 *
 * 描述：这种方式是最基本的实现方式，这种实现最大的问题就是不支持多线程。因为没有加锁 synchronized，所以严格意义上它并不算单例模式。
 * 这种方式 lazy loading 很明显，不要求线程安全，在多线程不能正常工作。
 *
 *
 */
public class LazyUnSafeSingleton {
    private static LazyUnSafeSingleton singleton;

    private LazyUnSafeSingleton() {
    }

    public static LazyUnSafeSingleton getInstance() {
        if (singleton == null) {
            return new LazyUnSafeSingleton();
        }
        return singleton;
    }
}
