package com.orecic.orderbook.infraestructure.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableAsync
public class CreateOrderAsyncConfigurer implements AsyncConfigurer {

    Logger logger = LoggerFactory.getLogger(CreateOrderAsyncConfigurer.class);


    @Override
    public Executor getAsyncExecutor() {
        logger.info("m=getAsyncExecutor USING_CUSTOM_EXECUTOR");
        return Executors.newFixedThreadPool(200);
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) -> logger.error("m=getAsyncUncaughtExceptionHandler Exception with message={} Method={} Number of parameters={}",
                ex.getMessage(), method, params.length);
    }
}

