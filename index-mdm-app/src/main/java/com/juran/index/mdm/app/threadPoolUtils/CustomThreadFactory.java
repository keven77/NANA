package com.juran.index.mdm.app.threadPoolUtils;


import com.juran.index.mdm.app.service.CaseindexService;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhangyihang on 2017年10月17日 15:17:03
 */
public class CustomThreadFactory implements ThreadFactory {
    private AtomicInteger count = new AtomicInteger(0);
    //实现自己的工厂模式 。
    @Override
    public Thread newThread (Runnable r) {
        Thread t = new Thread(r);
        t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println(t.getName() + count.addAndGet(1) + "Exception: " + e.toString());
            }
        });
        String threadName = CaseindexService.class.getSimpleName() + count.addAndGet(1);
        System.out.println(threadName);
        return t;
    }
}
