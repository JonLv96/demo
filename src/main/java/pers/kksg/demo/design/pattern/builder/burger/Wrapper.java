package pers.kksg.demo.design.pattern.builder.burger;

import pers.kksg.demo.design.pattern.builder.Packing;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/03/24/0:32
 * @Description:
 */
public class Wrapper implements Packing {
    @Override
    public String pack() {
        return "Wrapper";
    }
}
