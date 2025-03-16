package com.beholder.watch.dtos.usecases;

import com.beholder.watch.model.watchable.WatchableStatus;

public interface HttpResponseDetails {
  public int getResponseTime();
  public int getResponseStatus();
  public WatchableStatus getWatchableStatus();
  public String getErrorMessage();
}
