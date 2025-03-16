package com.beholder.watch.model.watchable;

import com.beholder.watch.model.watchable.WatchableStatus;

import java.util.Date;

public class Watchable {

  private Long id;

  private String name;

  private String url;

  private Integer checkInterval;

  private WatchableStatus status;

  private Date createdAt;

  private Date updatedAt;

  public Watchable(Long id, String name, String url, Integer checkInterval, WatchableStatus status, Date createdAt,
      Date updatedAt) {
    this.id = id;
    this.name = name;
    this.url = url;
    this.checkInterval = checkInterval;
    this.status = status;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public Watchable() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Integer getCheckInterval() {
    return checkInterval;
  }

  public void setCheckInterval(Integer checkInterval) {
    this.checkInterval = checkInterval;
  }

  public WatchableStatus getStatus() {
    return status;
  }

  public void setStatus(WatchableStatus status) {
    this.status = status;
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
