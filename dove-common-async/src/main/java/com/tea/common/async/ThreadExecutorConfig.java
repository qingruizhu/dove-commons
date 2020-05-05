package com.tea.common.async;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description: 任务线程池
 * @Auther: qingruizhu
 * @Date: 2020/4/20 18:07
 */
@Configuration
@EnableAsync//开启异步调用
public class ThreadExecutorConfig {
    @Value("${async.executor.thread.core_pool_size:5}")
    private int corePoolSize;
    @Value("${async.executor.thread.max_pool_size:10}")
    private int maxPoolSize;
    @Value("${async.executor.thread.queue_capacity:99}")
    private int queueCapacity;
    @Value("${async.executor.thread.name.prefix:async-service-}")
    private String threadNamePrefix;

    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(threadNamePrefix);
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 执行初始化
        executor.initialize();
        return executor.getThreadPoolExecutor();
    }

}
