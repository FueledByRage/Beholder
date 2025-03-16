package com.beholder.watch.usecases;

import java.util.List;

import com.beholder.watch.model.watchable.Watchable;

public interface StartWatchUseCase {

  public void init();

  public void start();

  public void start(int page, int size);

}