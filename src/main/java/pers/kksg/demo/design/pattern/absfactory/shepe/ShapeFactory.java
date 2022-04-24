package pers.kksg.demo.design.pattern.absfactory.shepe;

import org.apache.commons.lang.StringUtils;
import pers.kksg.demo.design.pattern.absfactory.AbstractFactory;
import pers.kksg.demo.design.pattern.absfactory.color.Color;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/03/23/18:48
 * @Description: 形状工厂
 */
public class ShapeFactory extends AbstractFactory {
    @Override
    public Color getColor(String color) {
        return null;
    }

    @Override
    public Shape getShape(String shapeType){
        if (StringUtils.isBlank(shapeType)) {
            return null;
        }
        switch (shapeType) {
            case "圆形":
                return new Circle();
            case "正方形":
                return new Square();
            case "长方形":
                return new Rectangle();
        }
        return null;
    }
}
