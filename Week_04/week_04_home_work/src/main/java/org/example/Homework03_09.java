package org.example;

import java.util.concurrent.*;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 * <p>
 * 一个简单的代码参考：
 */
public class Homework03_09 {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        try {
            long start = System.currentTimeMillis();
            // 在这里创建一个线程或线程池
            Worker worker = new Worker();
            Future<Integer> future = executorService.submit(worker);
            System.out.println("异步计算结果为：" + future.get());
            System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
            // 然后退出main线程
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if (a < 2)
            return 1;
        return fibo(a - 1) + fibo(a - 2);
    }

    static class Worker implements Callable<Integer> {
        @Override
        public Integer call() {
            return sum();
        }
    }
}
