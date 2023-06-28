package pers.kksg.demo.utils.bean.copy;

import cn.hutool.core.date.StopWatch;
import org.junit.jupiter.api.Test;
import pers.kksg.demo.entity.People;
import pers.kksg.demo.entity.People3;

import java.util.Random;
import java.util.stream.IntStream;

/**
 * @project demo
 * @description
 * @author lvqiang
 * @date 2023/6/27 14:45:50
 * @version 1.0
 */
class BeanCopierUtilTest {

    @Test
    void copy() {
        People people = new People();
        people.setAge(0);
        people.setName("tom");

        People people2 = new People();
        people2.setAge(0);
        people2.setName("tom");
        people2.hashCode();
        StopWatch watch = new StopWatch();
        watch.start("task1");
        IntStream.range(0, 10).forEach(i -> {
            people.setAge(i + new Random().nextInt());
            BeanCopierUtil.copy(people, People.class);
        });
        watch.stop();
        System.out.println(watch.prettyPrint());
    }

    @Test
    void testCopy() {
        People3 people3 = new People3();
        people3.setAge2(0);
        people3.setName2("kk");

        BeanCopierUtil.copy(people3, People.class);

        People3 people33 = new People3();
        people33.setAge2(99);
        people33.setName2("kk2");

        BeanCopierUtil.copy(people33, People.class);


    }

    @Test
    void copyWithNull() {
//        BeanCopierUtil.copyWithNull();
    }
}