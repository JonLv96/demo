package pers.kksg.demo.design.pattern.factory;

import org.apache.commons.lang.StringUtils;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/03/23/18:48
 * @Description: 形状工厂
 */
public class ShapeFactory {
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
