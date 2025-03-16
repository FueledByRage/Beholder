package com.beholder.watch.usecases;

import com.beholder.watch.dtos.usecases.HttpResponseDetails;

public interface WatchableMonitorUseCase {
  public void updateMetrics(String watchableName, HttpResponseDetails httpResponseDetails);
}
