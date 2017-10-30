package com.juran.index.mdm.app.threadPoolUtils;

import org.slf4j.LoggerFactory;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by zhangyihang on 2017年10月17日 15:12:58
 */
public class RejectedExecutionHandlerImpl implements RejectedExecutionHandler {
    private static  final org.slf4j.Logger logger = LoggerFactory.getLogger(RejectedExecutionHandlerImpl.class);
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

        try{
            // 核心改造点，由blockingqueue的offer改成put阻塞方法
            executor.getQueue().put(r);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("超过的线程需要在这里处理并且抛出异常",r.toString());
        }
    }
}
