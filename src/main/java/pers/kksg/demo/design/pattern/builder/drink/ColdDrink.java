package pers.kksg.demo.design.pattern.builder.drink;

import pers.kksg.demo.design.pattern.builder.Item;
import pers.kksg.demo.design.pattern.builder.Packing;
import pers.kksg.demo.design.pattern.builder.drink.Bottle;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/03/24/0:34
 * @Description:
 */
public abstract class ColdDrink implements Item {
    @Override
    public abstract String name();

    @Override
    public Packing packing() {
        return new Bottle();
    }

    @Override
    public abstract float price();
}
