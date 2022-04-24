package pers.kksg.demo.design.pattern.absfactory;

import org.apache.commons.lang.StringUtils;
import pers.kksg.demo.design.pattern.absfactory.color.ColorFactory;
import pers.kksg.demo.design.pattern.absfactory.shepe.ShapeFactory;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/03/23/21:24
 * @Description:
 */
public class FactoryProducer {
    public static AbstractFactory getFactory(String choice) {
        if (StringUtils.isBlank(choice)) {
            return null;
        }
        if (StringUtils.equals(choice, "Shape")) {
            return new ShapeFactory();
        } else if (StringUtils.equals(choice, "Color")) {
            return new ColorFactory();
        } else {
            return null;
        }
    }
}
