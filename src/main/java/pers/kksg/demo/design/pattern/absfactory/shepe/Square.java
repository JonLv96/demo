package pers.kksg.demo.design.pattern.absfactory.shepe;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/03/23/18:46
 * @Description: 正方形
 */
public class Square implements Shape {
    @Override
    public void draw() {
        System.out.println("正方形..");
    }
}
