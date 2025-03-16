package com.beholder.watch.adapters.outbound.application.services;

import com.beholder.watch.usecases.TaskManagerUseCase;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.beholder.watch.dtos.usecases.HttpResponseDetails;
import com.beholder.watch.model.Log;
import com.beholder.watch.model.watchable.Watchable;
import com.beholder.watch.model.watchable.WatchableStatus;
import com.beholder.watch.usecases.HttpUseCase;
import com.beholder.watch.usecases.LogUseCases;
import com.beholder.watch.usecases.WatchUseCase;
import com.beholder.watch.usecases.WatchableUseCases;
import com.beholder.watch.usecases.WatchableMonitorUseCase;

import org.springframework.http.HttpStatus;

import org.springframework.context.annotation.Lazy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class WatchService implements WatchUseCase {

  @Autowired
  private final LogUseCases logService;

  @Autowired
  private final HttpUseCase httpService;

  @Autowired
  private final JpaWatchableService watchableService;

  @Autowired
  private final WatchableMonitorUseCase watchableMonitorService;

  @Autowired
  private TaskManagerUseCase taskManager;

  public WatchService(LogUseCases logService, HttpUseCase httpService, @Lazy JpaWatchableService watchableService,
      WatchableMonitorUseCase watchableMonitorService, TaskManagerUseCase taskManager) {
    this.logService = logService;
    this.httpService = httpService;
    this.watchableService = watchableService;
    this.watchableMonitorService = watchableMonitorService;
    this.taskManager = taskManager;
  }


  @Override
  public void init() {
    this.start();
  }

  @Override
  public void start() {
    this.start(10, 0);
  }

  @Override
  public void start(int size, int page) {
      List<Watchable> watchables;
      int currentPage = page;
      
      do {
          watchables = watchableService.findByPage(size, currentPage);
          
          if (watchables.size() > 0) {
              final List<Watchable> currentWatchables = watchables;
              this.taskManager.sendToCluster(() -> this.startWatchables(currentWatchables));
              currentPage++;
          }
      } while (watchables.size() > 0);
      
      this.taskManager.stop();
  }

  public void watch(Watchable watchable) {
    HttpResponseDetails response = this.getHttpResponseDetails(watchable);

    this.updateWatchableStatus(watchable, response);

    this.saveLog(watchable, response);

    this.watchableMonitorService.updateMetrics(watchable.getName(), response);
  }

  private void startWatchables(List<Watchable> watchables) {
    watchables.stream()
        .forEach(watchable -> this.watch(watchable));
  }

  private void updateWatchableStatus(Watchable watchable, HttpResponseDetails response) {
    if (this.isServiceUnavailable(response)) {
      this.watchableService.updateWatchableStatus(watchable, WatchableStatus.UP);
    } else {
      this.watchableService.updateWatchableStatus(watchable, WatchableStatus.DOWN);
    }
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
