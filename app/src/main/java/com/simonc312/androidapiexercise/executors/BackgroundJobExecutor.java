package com.simonc312.androidapiexercise.executors;

import android.support.annotation.NonNull;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Simon on 5/22/2017.
 */

public class BackgroundJobExecutor extends ThreadPoolExecutor {

    public static BackgroundJobExecutor getInstance() {
        final int processors = Runtime.getRuntime().availableProcessors();
        return new BackgroundJobExecutor(
                processors, /*coreThreadPoolSize*/
                processors*2, /*maxThreadPoolSize*/
                60L, TimeUnit.SECONDS, /*non core keep alive*/
                new LinkedBlockingQueue<Runnable>(25), /*bounded capacity*/
                new JobThreadFactory(),
                new DiscardOldestPolicy()
        );
    }

    public BackgroundJobExecutor(int corePoolSize,
                                 int maximumPoolSize,
                                 long keepAliveTime, TimeUnit unit,
                                 BlockingQueue<Runnable> workQueue,
                                 ThreadFactory threadFactory,
                                 RejectedExecutionHandler rejectedExecutionHandler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, rejectedExecutionHandler);
    }

    private static class JobThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolCount = new AtomicInteger(1);
        final AtomicInteger threadCount = new AtomicInteger(1);
        private final String threadPrefix;

        protected JobThreadFactory() {
            this.threadPrefix = "background job pool:"
                    + JobThreadFactory.poolCount.getAndIncrement()
                    + ": thread: ";
        }

        @Override
        public Thread newThread(@NonNull Runnable runnable) {
            final Thread t = new Thread(runnable, this.threadPrefix + threadCount.getAndIncrement());
            t.setPriority(Thread.MIN_PRIORITY);
            return t;
        }
    }
}
