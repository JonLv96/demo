package pers.kksg.demo.basic;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.util.NumberUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import pers.kksg.demo.entity.OrderPO;
import pers.kksg.demo.utils.DesensitizedUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * ReentrantLockDemo
 *
 * @Author Jonlv
 * @Description 重入锁测试类
 * @Date 2022/5/23 15:54
 */
@Slf4j
public class ReentrantLockDemo {
    private static ReentrantLock lock = new ReentrantLock();

    public void doSomething(int n) {
        try {
            //进入递归第一件事：加锁
            log.info("--------lock()执行后，getState()的值：{} lock.isLocked():{}", lock.getHoldCount(), lock.isLocked());
            log.info("--------递归{}次--------", n);
            if (n <= 2) {
                this.doSomething(++n);
            } else {
                return;
            }
        } finally {
            lock.unlock();
            log.info("--------unlock()执行后，getState()的值：{} lock.isLocked():{}", lock.getHoldCount(), lock.isLocked());
        }
        CsvUtil
    }

    public static void main(String[] args) {
        System.out.println(CollectionUtils.containsInstance(Arrays.asList(0, 1, 2, 3), 7));

    }
}
