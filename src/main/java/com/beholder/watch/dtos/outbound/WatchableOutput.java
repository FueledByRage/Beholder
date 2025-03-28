package com.beholder.watch.dtos.outbound;

import com.beholder.watch.model.watchable.WatchableStatus;

public interface WatchableOutput {
  public Long getId();
  public String getName();
  public String getUrl();
  public Integer getCheckInterval();
  public WatchableStatus getStatus();
  public String getBody();
  public String getCredentials();
  public String getHttpMethod();
  public String getCredentialsName();
}
