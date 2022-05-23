package pers.kksg.demo.basic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

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
            lock.lock();
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
    }

    public static void main(String[] args) {
        ReentrantLockDemo reentrantLockDemo = new ReentrantLockDemo();
        reentrantLockDemo.doSomething(1);
        log.info("执行完doSomething方法 是否还持有锁：{}", lock.isLocked());
    }
}
