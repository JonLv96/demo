package pers.kksg.demo.design.pattern.builder;

import pers.kksg.demo.design.pattern.builder.burger.ChickenBurger;
import pers.kksg.demo.design.pattern.builder.burger.VegBurger;
import pers.kksg.demo.design.pattern.builder.drink.Coke;
import pers.kksg.demo.design.pattern.builder.drink.Pepsi;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/03/24/0:48
 * @Description:
 */
public class MealBuilder {
    public Meal prepareVegMeal(){
        Meal meal = new Meal();
        meal.addItem(new VegBurger());
        meal.addItem(new Coke());
        return meal;
    }
    public Meal prepareChickenMeal(){
        Meal meal = new Meal();
        meal.addItem(new ChickenBurger());
        meal.addItem(new Pepsi());
        return meal;
    }
}
