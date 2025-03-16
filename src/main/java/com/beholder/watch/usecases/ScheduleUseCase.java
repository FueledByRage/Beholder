package com.beholder.watch.usecases;

import java.util.List;

public interface ScheduleUseCase {

  public void scheduleTask(Runnable task, Long interval);

  public void scheduleTasks(List<Runnable> tasks, Long interval);
}
