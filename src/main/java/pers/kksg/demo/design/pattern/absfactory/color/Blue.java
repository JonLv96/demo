package pers.kksg.demo.design.pattern.absfactory.color;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/03/23/21:16
 * @Description:
 */
public class Blue implements Color{
    @Override
    public void fill() {
        System.out.println("Blue..");
    }
}
