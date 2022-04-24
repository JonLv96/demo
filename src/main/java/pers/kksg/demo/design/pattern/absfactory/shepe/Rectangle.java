package pers.kksg.demo.design.pattern.absfactory.shepe;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/03/23/18:43
 * @Description: 长方形
 */
public class Rectangle implements Shape {
    @Override
    public void draw() {
        System.out.println("长方形..");
    }
}
