package pers.kksg.demo.design.pattern.builder;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/03/24/0:51
 * @Description:
 */
public class BuilderDemo {
    public static void main(String[] args) {
        MealBuilder builder = new MealBuilder();
        Meal vegMeal = builder.prepareVegMeal();
        vegMeal.showItems();
        System.out.println(vegMeal.getCost());
    }
}
