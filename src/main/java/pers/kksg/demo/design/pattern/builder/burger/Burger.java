package pers.kksg.demo.design.pattern.builder.burger;

import pers.kksg.demo.design.pattern.builder.Item;
import pers.kksg.demo.design.pattern.builder.Packing;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/03/24/0:34
 * @Description:
 */
public abstract class Burger implements Item {
    @Override
    public abstract String name();

    @Override
    public Packing packing() {
        return new Wrapper();
    }

    @Override
    public abstract float price();
}
