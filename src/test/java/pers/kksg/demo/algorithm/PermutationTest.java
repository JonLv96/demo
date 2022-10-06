package pers.kksg.demo.algorithm;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/09/30/15:21
 * @Description:
 */
class PermutationTest {

    @Test
    void permutation() {
        Permutation permutation = new Permutation();
        String[] strings = permutation.permutation("qqe");
        System.out.println(JSON.toJSON(strings));


    }
}