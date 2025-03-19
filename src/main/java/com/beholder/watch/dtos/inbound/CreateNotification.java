package com.beholder.watch.dtos.inbound;

public interface CreateNotification {
  public String getWatchableName();
  public String getMessage();
  public String getSentAt();
}
