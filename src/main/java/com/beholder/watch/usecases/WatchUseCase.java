package com.beholder.watch.usecases;

import com.beholder.watch.model.watchable.Watchable;

public interface WatchUseCase {
  public void init();
  public void start();
  public void start(int page, int size);
  public void watch(Watchable watchable);
}
