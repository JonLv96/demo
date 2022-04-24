package pers.kksg.demo;

import java.sql.PreparedStatement;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/03/30/14:26
 * @Description:
 */
public class Singleton {
    private volatile static Singleton singleton;


    public static  Singleton getInstance(){
        if (null == singleton) {
            synchronized (Singleton.class) {
                if (null == singleton) {
                    singleton = new Singleton();
                }
            }

        }
        return singleton;
    }


    public static void main(String[] args) {
        System.out.println(demo());;
    }
    public static int demo(){
        int i = 10;
        try {
            int c=  i/0;
            return --i;
        } catch (Exception e) {
            return --i;
        } finally {
            --i;
        }
    }



}
