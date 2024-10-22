package com.example.backendeventmanagementbooking.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Slf4j
@Configuration
public class AsyncConfig {

    @Bean(name = {"async", "email"})
    public Executor asyncTaskExecutor() {
        log.info("Task executor initialized");
        var cpuCores = Runtime.getRuntime().availableProcessors();
        var poolSize = calculateCorePoolSize(cpuCores);
        var maxPoolSize = calculateMaxPoolSize(cpuCores);
        var queueCapacity = calculateQueueSupport(cpuCores);

        log.info("CPU Cores: {}", cpuCores);
        log.info("Pool Size: {}", poolSize);
        log.info("Queue Capacity: {}", queueCapacity);

        final var executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(poolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("Async Thread - ");
        executor.initialize();
        return executor;
    }

    private static int calculateCorePoolSize(int cpuCores) {
        return (int) (cpuCores * 0.75);
    }

    private static int calculateMaxPoolSize(int cpuCores) {
        return cpuCores * 2;
    }

    private static int calculateQueueSupport(int cpuCores) {
        return cpuCores * 15;
    }

}
