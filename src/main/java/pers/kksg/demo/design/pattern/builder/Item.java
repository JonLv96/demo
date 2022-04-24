package pers.kksg.demo.design.pattern.builder;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/03/24/0:30
 * @Description:
 */
public interface Item {
    String name();

    Packing packing();

    float price();

}
