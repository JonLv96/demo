package pers.kksg.demo.design.pattern.factory;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/03/23/19:00
 * @Description: 形状工厂测试类
 */
class ShapeFactoryTest {

    @Test
    void testGetShape() {
        ShapeFactory shapeFactory = new ShapeFactory();
        Shape rectangle = shapeFactory.getShape("长方形");
        rectangle.draw();
        Shape square = shapeFactory.getShape("正方形");
        square.draw();
        Shape circle = shapeFactory.getShape("圆形");
        circle.draw();
    }


    public static List<Integer> getIndex(List<Integer> list, Integer number) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < list.size() -1; i++) {
            for (int j = i+1; j < list.size(); j++) {
                if (list.get(i) + list.get(j) == number) {
                    result.add(i);
                    result.add(j);
                }
            }
        }
        return result;
    }


    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 2, 3, 3, 4, 5);
        int number = 6;
        List<Integer> result = getIndex(list, number);
        System.out.println(JSON.toJSON(result));
    }
}