package pers.kksg.demo.entity;

import lombok.Data;

/**
 * @project demo
 * @description 人类
 * @author lvqiang
 * @date 2023/1/12 18:39:29
 * @version 1.0
 */
@Data
public class People3 {
    private Integer age2;
    private String name2;



    @Override
    public int hashCode() {
        int result = 1;
        Object $age2 = this.getAge2();
        result = result * 59 + ($age2 == null ? 43 : $age2.hashCode());
        Object $name2 = this.getName2();
        result = result * 59 + ($name2 == null ? 43 : $name2.hashCode());
        System.out.println("People3----" + toString());
        System.out.println("age2 ----" +  $age2.hashCode());
        System.out.println("$name2 ----" +  $name2.hashCode());
        System.out.println("result ----" +  result);
        return result;
    }
}
