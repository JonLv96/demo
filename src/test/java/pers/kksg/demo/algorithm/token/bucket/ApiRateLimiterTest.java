package pers.kksg.demo.algorithm.token.bucket;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

/**
 * @project demo
 * @description
 * @author lvqiang
 * @date 2023/6/29 16:37:57
 * @version 1.0
 */
class ApiRateLimiterTest {

    @Test
    void isAllowed() {
        Map<Boolean, Integer> map = new ConcurrentHashMap<>();

        IntStream.range(0, 15).parallel().forEach(i -> {
            boolean allowed = ApiRateLimiter.isAllowed();
            map.compute(allowed, (key, value) -> Objects.isNull(value) ? 1 : value + 1);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        });
        System.out.println(JSON.toJSONString(map));
    }

}