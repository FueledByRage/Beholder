package com.beholder.watch.adapters.outbound.application.services;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.ExecutorService;

import com.beholder.watch.usecases.WatchableUseCases;
import com.beholder.watch.usecases.TaskManagerUseCase;
import com.beholder.watch.usecases.ScheduleUseCase;

@Service
@RequiredArgsConstructor
public class TaskManagerService implements TaskManagerUseCase {

  private final WatchableUseCases watchableService;
  private final ExecutorService cluster;
  private final ScheduleUseCase scheduleService;

  @Override
  public void sendToCluster(Runnable task) {
    cluster.submit(task);
  }

  @Override
  public void stop() {
    cluster.shutdown();
  }
}
