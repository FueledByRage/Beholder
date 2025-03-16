package com.beholder.watch.usecases;

import com.beholder.watch.dtos.usecases.HttpResponseDetails;

public interface HttpUseCase {
  public HttpResponseDetails getRequest(String url);
}
