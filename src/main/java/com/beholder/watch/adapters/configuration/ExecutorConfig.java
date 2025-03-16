package com.beholder.watch.adapters.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ExecutorConfig {

  @Bean
  public ExecutorService taskExecutor(@Value("${taskmanager.thread-pool-size}") int threadPoolSize) {
    return Executors.newFixedThreadPool(threadPoolSize);
  }
}
