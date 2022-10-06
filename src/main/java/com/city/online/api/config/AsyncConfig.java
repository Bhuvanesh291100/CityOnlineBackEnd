package com.city.online.api.config;

import com.city.online.api.config.usercontext.UserContext;
import com.city.online.api.config.usercontext.UserThreadLocal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {
    @Value("${async.testexecutor.corepoolsize:10}")
    private int corePoolSize;

    @Value("${async.testexecutor.maxcorepoolsize:10}")
    private int maxcorepoolsize;

    @Value("${async.testexecutor.queuecapacity:10}")
    private int queueCapacity;

    @Bean(name = "testExecutor")
    public ThreadPoolTaskExecutor preCacheExecutor() {
        ThreadPoolTaskExecutor  threadPoolTaskExecutor = getThreadpoolTaskExecutor();
        return threadPoolTaskExecutor;
    }

    @Bean(name = "inventryExecutor")
    public ThreadPoolTaskExecutor inventryExecutor() {
        ThreadPoolTaskExecutor  threadPoolTaskExecutor = getThreadpoolTaskExecutor();
        return threadPoolTaskExecutor;
    }

    private ThreadPoolTaskExecutor getThreadpoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
        threadPoolTaskExecutor.setMaxPoolSize(maxcorepoolsize);
        threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        threadPoolTaskExecutor.setAwaitTerminationSeconds(60);

        return threadPoolTaskExecutor;
    }

    @Async("testExecutor")
    public CompletableFuture<Object> getObject(String testId, UserContext userContext) {
        UserThreadLocal.setUserContext(userContext);
        String response = "Async Response";

        return CompletableFuture.completedFuture(response);
    }

}
