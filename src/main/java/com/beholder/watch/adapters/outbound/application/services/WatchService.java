package com.beholder.watch.adapters.outbound.application.services;

import com.beholder.watch.usecases.TaskManagerUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import org.springframework.stereotype.Service;

import com.beholder.watch.adapters.inbound.dtos.HttpResponseDetailsDto;
import com.beholder.watch.dtos.usecases.HttpResponseDetails;
import com.beholder.watch.model.Log;
import com.beholder.watch.model.watchable.Watchable;
import com.beholder.watch.model.watchable.WatchableStatus;
import com.beholder.watch.usecases.HttpUseCase;
import com.beholder.watch.usecases.LogUseCases;
import com.beholder.watch.usecases.ScheduleUseCase;
import com.beholder.watch.usecases.WatchUseCase;
import com.beholder.watch.usecases.WatchableUseCases;
import com.beholder.watch.usecases.WatchableMonitorUseCase;

import org.springframework.http.HttpStatus;

import org.springframework.context.annotation.Lazy;

import java.util.List;

import javax.annotation.PostConstruct;


@Service
public class WatchService implements WatchUseCase {

  private final LogUseCases logService;
  private final HttpUseCase httpService;
  private final JpaWatchableService watchableService;
  private final WatchableMonitorUseCase watchableMonitorService;
  private final TaskManagerUseCase taskManager;
  private final ScheduleUseCase scheduleService;
  
  private static final Logger logger = LoggerFactory.getLogger(WatchService.class);
  
  private static final int DEFAULT_PAGE_SIZE = 10;
  private static final int DEFAULT_START_PAGE = 0;
  private static final int MAX_PAGES = 1000;

  public WatchService(LogUseCases logService, 
                     HttpUseCase httpService, 
                     @Lazy JpaWatchableService watchableService,
                     WatchableMonitorUseCase watchableMonitorService, 
                     TaskManagerUseCase taskManager, 
                     ScheduleUseCase scheduleService) {
    this.logService = logService;
    this.httpService = httpService;
    this.watchableService = watchableService;
    this.watchableMonitorService = watchableMonitorService;
    this.taskManager = taskManager;
    this.scheduleService = scheduleService;
  }

  @PostConstruct
  @Override
  public void init() {
    logger.info("Initializing WatchService");
    this.start();
  }

  @Override
  public void start() {
    this.start(DEFAULT_PAGE_SIZE, DEFAULT_START_PAGE);
  }

  @Override
  public void start(int size, int page) {
    try {
      logger.info("Starting watchable monitoring with page size: {}, starting from page: {}", size, page);

      int currentPage = page;
      int pageCount = 0;
      
      boolean hasWatchablesLeft = true;

      while (hasWatchablesLeft && pageCount < MAX_PAGES) {
        List<Watchable> watchables = this.watchableService.findByPage(size, currentPage);

        if (watchables.isEmpty()) {
          hasWatchablesLeft = false;

          logger.info("No more watchables found after processing {} pages", pageCount);
        } else {
          logger.debug("Found {} watchables on page {}", watchables.size(), currentPage);

          this.taskManager.sendToCluster(() -> this.startWatchables(watchables));

          currentPage++;
          pageCount++;
        }
      }
      
      if (pageCount >= MAX_PAGES) {
        logger.warn("Reached maximum page limit ({}). Some watchables may not be monitored.", MAX_PAGES);
      }
      
    } catch (Exception e) {
      logger.error("Error starting watchable monitoring", e);
    }
  }

  public void watch(Watchable watchable) {
      HttpResponseDetails response = this.getHttpResponseDetails(watchable);

      this.updateWatchableStatus(watchable.getId(), response);

      this.saveLog(watchable, response);

      this.watchableMonitorService.updateMetrics(watchable.getName(), response);
    }

  private void startWatchables(List<Watchable> watchables) {
    try {
      for (Watchable watchable : watchables) {
        scheduleService.scheduleTask(
            () -> this.watch(watchable),
            watchable.getCheckInterval().longValue()
        );

        logger.debug("Scheduled watchable: {} with interval: {} seconds", 
                     watchable.getName(), watchable.getCheckInterval());
      }
    } catch (Exception e) {
      logger.error("Error scheduling watchables", e);
    }
  }

  private void updateWatchableStatus(long id, HttpResponseDetails response) {
    WatchableStatus status = isServiceUnavailable(response) ? WatchableStatus.DOWN : WatchableStatus.UP;

    this.watchableService.updateWatchableStatus(id, status);
  }

  private boolean isServiceUnavailable(HttpResponseDetails response) {
    return response.getResponseStatus() == HttpStatus.SERVICE_UNAVAILABLE.value();
  }

  private HttpResponseDetails getHttpResponseDetails(Watchable watchable) {
    return httpService.getRequest(watchable.getUrl());
  }

  private void saveLog(Watchable watchable, HttpResponseDetails response) {
    Log log = new Log(watchable, response.getResponseTime(), response.getResponseStatus(), response.getErrorMessage());

    this.logService.save(log);
  }
}