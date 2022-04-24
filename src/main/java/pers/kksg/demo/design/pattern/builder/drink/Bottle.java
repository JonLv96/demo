package pers.kksg.demo.design.pattern.builder.drink;

import pers.kksg.demo.design.pattern.builder.Packing;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/03/24/0:33
 * @Description:
 */
public class Bottle implements Packing {
    @Override
    public String pack() {
        return "Bottle";
    }
}
