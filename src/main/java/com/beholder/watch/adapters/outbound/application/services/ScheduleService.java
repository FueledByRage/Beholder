package com.beholder.watch.adapters.outbound.application.services;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import lombok.RequiredArgsConstructor;

import com.beholder.watch.usecases.ScheduleUseCase;

@Service
@RequiredArgsConstructor
public class ScheduleService implements ScheduleUseCase {

  private final TaskScheduler taskScheduler;

  @Override
  public void scheduleTask(Runnable task, Long interval) {
    taskScheduler.scheduleAtFixedRate(task, interval);
  }

  @Override
  public void scheduleTasks(List<Runnable> tasks, Long interval) {
    tasks.forEach(task -> taskScheduler.scheduleAtFixedRate(task, interval));
  }
}
