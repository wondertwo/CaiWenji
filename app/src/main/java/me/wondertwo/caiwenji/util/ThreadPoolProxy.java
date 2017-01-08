package me.wondertwo.caiwenji.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池代理类，基于ThreadPoolExecutor封装
 *
 * Created by wondertwo on 2016/12/23.
 */

public class ThreadPoolProxy {

    private ThreadPoolExecutor executor; //线程执行者
    private int  corePoolSize; //核心线程数
    private int  maxPoolSize; //最大线程数
    private long keepAliveTime; //线程存活时间
    private final static byte[] mLocker = new byte[0]; //同步锁

    public ThreadPoolProxy(int corePoolSize, int maxPoolSize, long keepAliveTime) {
        this.corePoolSize = corePoolSize;
        this.maxPoolSize = maxPoolSize;
        this.keepAliveTime = keepAliveTime;
        createExecutor();
    }

    private void createExecutor() {
        /**
         * 参1:核心线程数;
         * 参2:最大线程数;
         * 参3:线程休眠时间;
         * 参4:时间单位;
         * 参5:线程队列;
         * 参6:生产线程的工厂;
         * 参7:线程异常处理策略
         */
        this.executor = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                keepAliveTime,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
    }

    private synchronized ThreadPoolExecutor getExecutor() {
        return executor;
    }

    //执行任务
    public void execute(Runnable runnable) {
        getExecutor().execute(runnable);
    }

    //暂停执行
    private BlockingQueue<Runnable> shutdown() {
        ThreadPoolExecutor executer = getExecutor();
        executer.shutdown(); //停止执行队列中还未执行任务，当前正在执行任务会继续执行完成
        return executer.getQueue();
    }

    //强制暂停
    private synchronized BlockingQueue<Runnable> forceShutdown() {
        ThreadPoolExecutor executer = getExecutor();
        executer.shutdownNow(); //停止执行所有任务，包括当前正在执行的任务
        return executer.getQueue();
    }

    //移除任务
    public void remove(Runnable runnable) {
        getExecutor().remove(runnable);
    }

    //取消任务
    public void cancel(Runnable runnable) {
        getExecutor().getQueue().remove(runnable);
    }

    //提交任务
    public Future<?> submit(Runnable runnable) {
        return getExecutor().submit(runnable);
    }

}
