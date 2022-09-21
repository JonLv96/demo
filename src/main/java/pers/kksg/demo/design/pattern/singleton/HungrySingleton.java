package pers.kksg.demo.design.pattern.singleton;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/03/23/22:48
 * @Description: 饿汉式，该方式在类装载就会进行实例化，但是促发类装载的原因有很多种，这就达不到懒加载的效果
 */
public class HungrySingleton {
    private static HungrySingleton instance = new HungrySingleton();

    private HungrySingleton() {
    }

    public static HungrySingleton getInstance() {
        return instance;
    }
}
