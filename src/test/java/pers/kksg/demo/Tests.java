package pers.kksg.demo;

import cn.hutool.core.date.StopWatch;
import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pers.kksg.demo.entity.OrderPO;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
class Tests {

    @Test
    void test() {
        List<OrderPO> list = Arrays.asList(
                new OrderPO(1, 10),
                new OrderPO(2, 10),
                new OrderPO(3, 10),
                new OrderPO(4, 10),
                new OrderPO(1, 10),
                new OrderPO(1, 10));

        Map<Integer, OrderPO> map = list.stream().collect(Collectors.toMap(OrderPO::getId, Function.identity(), (v1, v2) -> {
            v1.setOrderNumber(v1.getOrderNumber() + v2.getOrderNumber());
            return v1;
        }));
        System.out.println(JSON.toJSON(map));
    }

    @Test
    void test2() {
        System.out.println(Objects.equals(null,new OrderPO()));
        System.out.println(null == new OrderPO());

    }
    @Test
    void test3() {
        StopWatch watch = new StopWatch();

        watch.start("arrayList");
        List<String> arrayList = new ArrayList<>();
        IntStream.range(0,10000).parallel().forEach(i ->{
            arrayList.add(String.valueOf(i));
        });
        watch.stop();

        watch.start("syncList");
        List<String> syncList = Collections.synchronizedList(new ArrayList<>());
        IntStream.range(0,10000).parallel().forEach(i ->{
            syncList.add(String.valueOf(i));
        });
        watch.stop();

        watch.start("vector");
        List<String> vector = new Vector<>();
        IntStream.range(0,10000).parallel().forEach(i ->{
            vector.add(String.valueOf(i));
        });
        watch.stop();

        watch.start("copyOnWriteArrayList");
        List<String> copyOnWriteArrayList = new CopyOnWriteArrayList();
        IntStream.range(0,10000).parallel().forEach(i ->{
            copyOnWriteArrayList.add(String.valueOf(i));
        });
        watch.stop();


        System.out.println(watch.prettyPrint());

        System.out.println(arrayList.size());
        System.out.println(syncList.size());
        System.out.println(vector.size());
        System.out.println(copyOnWriteArrayList.size());

    }

    @Test
    public void test33() {
        List<String> syncList = Collections.synchronizedList(new ArrayList<>());
        IntStream.range(0,100).parallel().forEach(i ->{
            syncList.add(String.valueOf(i));
        });

        Iterator<String> iterator = syncList.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            System.out.println(next);
        }
    }
}
