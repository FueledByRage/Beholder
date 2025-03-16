package com.beholder.watch.model;

import com.beholder.watch.model.watchable.Watchable;

import java.util.Date;

public class Log {

  private Long id;

  private Watchable watchable;

  private long responseTime;

  private int responseStatus;

  private String errorMessage;

  private Date createdAt;

  private Date updatedAt;

  public Log(Long id, Watchable service, long responseTime, int responseStatus, String errorMessage, Date createdAt,
      Date updatedAt) {
    this.id = id;
    this.watchable = service;
    this.responseTime = responseTime;
    this.responseStatus = responseStatus;
    this.errorMessage = errorMessage;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public Log(Watchable service, long responseTime, int responseStatus, String errorMessage) {
    this.watchable = service;
    this.responseTime = responseTime;
    this.responseStatus = responseStatus;
    this.errorMessage = errorMessage;
  }

  public Log() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Watchable getWatchable() {
    return watchable;
  }

  public void setWatchable(Watchable service) {
    this.watchable = service;
  }

  public long getResponseTime() {
    return responseTime;
  }

  public void setResponseTime(long responseTime) {
    this.responseTime = responseTime;
  }

  public int getResponseStatus() {
    return responseStatus;
  }

  public void setResponseStatus(int responseStatus) {
    this.responseStatus = responseStatus;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }
}
