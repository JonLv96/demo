package pers.kksg.demo.design.pattern.prototype;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/03/23/18:47
 * @Description: 圆形
 */
public class Circle extends Shape {
    public Circle() {
        type = "Circle";
    }



    @Override
    public void draw() {
        System.out.println("圆形..");
    }
}
