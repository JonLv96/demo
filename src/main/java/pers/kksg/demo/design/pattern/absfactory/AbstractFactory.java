package pers.kksg.demo.design.pattern.absfactory;

import pers.kksg.demo.design.pattern.absfactory.color.Color;
import pers.kksg.demo.design.pattern.absfactory.shepe.Shape;

import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/03/23/21:20
 * @Description: 抽象工厂
 */
public abstract class AbstractFactory {
    public abstract Color getColor(String color);
    public abstract Shape getShape(String shape);
}