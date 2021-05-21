package com.yapp.ios1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * created by jg 2021/05/21
 */
@Configuration
public class AsyncConfig {

    @Bean("asyncTask")
    public Executor threadPoolExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("asyncTask-"); // thread 이름 설정
        executor.setCorePoolSize(5);  // 동시에 실행시킬 쓰래드의 개수를 의미
        executor.setMaxPoolSize(100); // 쓰레드 풀의 최대 사이즈
        executor.setQueueCapacity(0); // 쓰레드 풀 큐의 사이즈. corePoolSize 개수를 넘어서는 task가 들어왔을 때
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        return executor;
    }
}
