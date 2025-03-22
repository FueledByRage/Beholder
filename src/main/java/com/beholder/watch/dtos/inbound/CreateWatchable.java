package com.beholder.watch.dtos.inboud;

public interface CreateWatchable {
  public String getName();
  public String getUrl();
  public Integer getCheckInterval();
  public String getBody();
  public String getCredentials();
  public String getCredentialsName();
}
