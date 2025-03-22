package com.beholder.watch.dtos.usecases;

public interface HttpRequestDetails {
  public String getUrl();
  public String getBody();
  public String getCredentials();
  public String getCredentialsName();
}
