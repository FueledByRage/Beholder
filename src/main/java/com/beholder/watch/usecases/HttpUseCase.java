package com.beholder.watch.usecases;

import com.beholder.watch.dtos.usecases.HttpRequestDetails;
import com.beholder.watch.dtos.usecases.HttpResponseDetails;

public interface HttpUseCase {
  public HttpResponseDetails getRequest(HttpRequestDetails requestDetails);
  public HttpResponseDetails postRequest(HttpRequestDetails requestDetails);
}
