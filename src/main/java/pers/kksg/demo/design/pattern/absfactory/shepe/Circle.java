package pers.kksg.demo.design.pattern.absfactory.shepe;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/03/23/18:47
 * @Description: 圆形
 */
public class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("圆形..");
    }
}
