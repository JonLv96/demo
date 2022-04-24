package pers.kksg.demo.design.pattern.builder.drink;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/03/24/0:40
 * @Description:
 */
public class Pepsi extends ColdDrink{
    @Override
    public String name() {
        return "Pepsi Drink";
    }

    @Override
    public float price() {
        return 35.0f;
    }
}
