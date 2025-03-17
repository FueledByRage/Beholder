package com.beholder.watch.adapters.outbound.application.services;


import com.beholder.watch.dtos.usecases.HttpResponseDetails;
import com.beholder.watch.usecases.WatchableMonitorUseCase;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.ConcurrentHashMap;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;

import com.beholder.watch.model.watchable.WatchableStatus;

@Service
public class WatchableMonitorService implements WatchableMonitorUseCase{
  
  private final MeterRegistry registry;
  private final ConcurrentHashMap<String, AtomicInteger> serviceStatuses = new ConcurrentHashMap<>();
  private final ConcurrentHashMap<String, AtomicInteger> responseStatuses = new ConcurrentHashMap<>();
  private final ConcurrentHashMap<String, Counter> requestCounters = new ConcurrentHashMap<>();
  private final ConcurrentHashMap<String, Timer> responseTimers = new ConcurrentHashMap<>();

  public WatchableMonitorService(MeterRegistry registry) {
        this.registry = registry;
}


  @Override
  public void updateMetrics(String watchableName, HttpResponseDetails httpResponseDetails) {
        this.updateServiceStatus(watchableName, httpResponseDetails.getWatchableStatus());
        this.updateResponseStatus(watchableName, httpResponseDetails.getResponseStatus());
        this.recordResponseTime(watchableName, httpResponseDetails.getResponseTime());
        this.recordRequest(watchableName);
        this.recordErrorMessage(watchableName, httpResponseDetails.getErrorMessage());
  }

  private void updateServiceStatus(String serviceName, WatchableStatus watchableStatus) {
        int status = watchableStatus == WatchableStatus.UP ? 1 : 0;

        serviceStatuses.computeIfAbsent(serviceName, name -> {
            AtomicInteger statusValue = new AtomicInteger(status);
            Gauge.builder("service_status", statusValue, AtomicInteger::get)
                .tag("service", name)
                .description("Status do serviço (1 = UP, 0 = DOWN)")
                .register(registry);
            return statusValue;
        }).set(status);
    }

    private void updateResponseStatus(String serviceName, int responseStatus) {
        responseStatuses.computeIfAbsent(serviceName, name -> {
            AtomicInteger statusValue = new AtomicInteger();
            Gauge.builder("service_response_status", statusValue, AtomicInteger::get)
                .tag("service", name)
                .description("Status da resposta do serviço")
                .register(registry);
            return statusValue;
        }).set(responseStatus);
    }

    private void recordRequest(String serviceName) {
        requestCounters.computeIfAbsent(serviceName, name ->
            Counter.builder("service_requests_total")
                .tag("service", name)
                .description("Total de requisições para o serviço")
                .register(registry)
        ).increment();
    }

    private void recordResponseTime(String serviceName, long responseTime) {
            responseTimers.computeIfAbsent(serviceName, name ->
                Timer.builder("service_response_time")
                    .tag("service", name)
                    .description("Tempo de resposta do serviço em milissegundos")
                    .publishPercentiles(0.5, 0.9, 0.99)
                    .register(registry)
            ).record(responseTime, TimeUnit.MILLISECONDS);
            
            registry.gauge("service_last_response_time", 
                           Tags.of("service", serviceName), 
                           new AtomicLong(responseTime));
    }

    private void recordErrorMessage(String serviceName, String errorMessage) {
        if (errorMessage == null || errorMessage.isEmpty()) {
            return;
        }
                    
        String truncatedError = this.getTruncatedErrorMessage(errorMessage);
        
        Counter.builder("service_error_count")
            .tag("service", serviceName)
            .tag("error_message", truncatedError)
            .description("Quantidade de erros por serviço")
            .register(registry)
            .increment();
            
    }

    private String getTruncatedErrorMessage(String errorMessage) {
        return errorMessage.length() > 100 ? 
               errorMessage.substring(0, 100) + "..." : 
               errorMessage;
    }
}
