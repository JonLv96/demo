package pers.kksg.demo.algorithm.token.bucket;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @project demo
 * @description ApiRateLimiter
 * @author lvqiang
 * @date 2023/6/29 16:18:11
 * @version 1.0
 */
public class ApiRateLimiter {
    private static final int TOKEN_BUCKET_SIZE = 10; // 令牌桶大小
    private static final int REFILL_RATE = 2; // 令牌桶每秒填充速率

    private static AtomicInteger tokenBucket = new AtomicInteger(TOKEN_BUCKET_SIZE);
    private static long lastRefillTime = System.currentTimeMillis() / 1000;

    public static boolean isAllowed() {
        refillTokenBucket();

        int tokens = tokenBucket.get();
        if (tokens > 0) {
            tokenBucket.decrementAndGet();
            return true; // 允许调用接口
        }

        return false; // 不允许调用接口
    }

    private static void refillTokenBucket() {
        long currentTime = System.currentTimeMillis() / 1000;
        long elapsedTime = currentTime - lastRefillTime;
        int tokensToRefill = (int) (elapsedTime * REFILL_RATE);

        if (tokensToRefill > 0) {
            while (true) {
                int currentTokens = tokenBucket.get();

                int newTokens = Math.min(currentTokens + tokensToRefill, TOKEN_BUCKET_SIZE);
                if (tokenBucket.compareAndSet(currentTokens, newTokens)) {
                    lastRefillTime = currentTime;
                    break;
                }
            }
        }
    }
}
