package pers.kksg.demo.design.pattern.builder.drink;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/03/24/0:40
 * @Description:
 */
public class Coke extends ColdDrink{
    @Override
    public String name() {
        return "Coke Drink";
    }

    @Override
    public float price() {
        return 30.0f;
    }
}
