package net.oschina.git.justlive1.breeze.cloud.registry.client;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import net.oschina.git.justlive1.breeze.cloud.registry.client.BaseRegistryClient;

/**
 * 客户端注册单元测试
 * 
 * @author wubo
 *
 */
public class BaseRegistryClientTest {

    /**
     * 测试register方法的线程安全
     * 
     * @throws InterruptedException
     */
    @Test
    public void testThreadSafe() throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(1);
        int size = 10;
        ThreadPoolExecutor pool = new ThreadPoolExecutor(size, size, 0L, TimeUnit.MICROSECONDS,
                new LinkedBlockingQueue<Runnable>(10),
                new ThreadFactoryBuilder().setNameFormat("register-pool-").build());
        ExampleRegistryClient client = new ExampleRegistryClient();

        for (int i = 0; i < size; i++) {
            pool.execute(() -> {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                client.register();
                client.start();
                client.shutdown();
            });
        }

        TimeUnit.SECONDS.sleep(1);
        latch.countDown();

        TimeUnit.SECONDS.sleep(1);
        Assert.assertEquals(3, client.count.get());

        pool.shutdown();

    }

    static class ExampleRegistryClient extends BaseRegistryClient {

        private AtomicInteger count = new AtomicInteger(0);

        @Override
        protected void doRegister() {
            count.incrementAndGet();
        }

        @Override
        protected void doStart() {
            count.incrementAndGet();
        }

        @Override
        protected void doShutdown() {
            count.incrementAndGet();
        }

    }
}
