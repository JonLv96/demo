package pers.kksg.demo.design.pattern.prototype;

import com.alibaba.fastjson.JSON;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/03/24/16:48
 * @Description:
 */
public class PrototypeDemo {
    public static void main(String[] args) {
        ShapeCache.loadCache();

        Shape shape = ShapeCache.getShape("1");
        System.out.println(JSON.toJSON(shape));

        Shape shape1 = new Rectangle();
        System.out.println(shape1);
        System.out.println(shape1.clone());
    }
}