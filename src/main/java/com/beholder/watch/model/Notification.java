package com.beholder.watch.model;

import java.util.Date;

import com.beholder.watch.model.watchable.Watchable;

public class Notification {

  private Long id;

  private Watchable watchable;

  private String message;

  private Date sentAt;

  public Notification(Long id, Watchable service, String message, Date sentAt) {
    this.id = id;
    this.watchable = service;
    this.message = message;
    this.sentAt = sentAt;
  }
  
  public Notification(Watchable service, String message, Date sentAt) {
    this.watchable = service;
    this.message = message;
    this.sentAt = sentAt;
  }

  public Notification() {
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

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Date getSentAt() {
    return sentAt;
  }

  public void setSentAt(Date sentAt) {
    this.sentAt = sentAt;
  }
}
