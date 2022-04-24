package pers.kksg.demo.design.pattern.absfactory.color;

import org.apache.commons.lang.StringUtils;
import pers.kksg.demo.design.pattern.absfactory.AbstractFactory;
import pers.kksg.demo.design.pattern.absfactory.shepe.Shape;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/03/23/21:16
 * @Description:
 */
public class ColorFactory extends AbstractFactory {
    @Override
    public Color getColor(String color) {
        if (StringUtils.isBlank(color)) {
            return null;
        }
        switch (color) {
            case "Red":
                return new Red();
            case "Green":
                return new Green();
            case "Blue":
                return new Blue();
            default:
                return null;
        }
    }

    @Override
    public Shape getShape(String shape) {
        return null;
    }
}
