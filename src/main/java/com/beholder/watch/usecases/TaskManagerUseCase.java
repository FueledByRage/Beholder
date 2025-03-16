package com.beholder.watch.usecases;

public interface TaskManagerUseCase {

  public void sendToCluster(Runnable task);

  public void stop();
}
