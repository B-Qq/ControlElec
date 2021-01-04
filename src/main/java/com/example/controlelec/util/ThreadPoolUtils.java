package com.example.controlelec.util;

import com.example.controlelec.comm.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : liuze
 * @create 2019/7/9 9:26
 */
public class ThreadPoolUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadPoolUtils.class);

    /**
     * 线程池对象
     */
    private static ThreadPoolExecutor threadPoolExecutor;


    public static ThreadPoolExecutor getThreadPoolExecutor(){
        long tc=0;
        if(threadPoolExecutor!=null) {
            tc= threadPoolExecutor.getTaskCount();
        }
        if (null == threadPoolExecutor||tc>=2000){
            synchronized (ThreadPoolUtils.class){
                if (null == threadPoolExecutor||tc>=2000){
                    if(threadPoolExecutor!=null) {
                        threadPoolExecutor.shutdown();
                    }
                    Integer corePoolSize = Integer.valueOf(Config.getConstant("ThreadPoolExecutor.corePoolSize"));
                    Integer maximumPoolSize = Integer.valueOf(Config.getConstant("ThreadPoolExecutor.maximumPoolSize"));
                    long keepAliveTime = Long.valueOf(Config.getConstant("ThreadPoolExecutor.keepAliveTime"));
                    BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(100);

                    threadPoolExecutor = new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime, TimeUnit.SECONDS,
                            blockingQueue,Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());

                }
            }

        }

        return threadPoolExecutor;
    }


}
