package com.beholder.watch.model.watchable;

import com.beholder.watch.model.watchable.WatchableStatus;

import java.util.Date;

public class Watchable {

  private Long id;

  private String name;

  private String url;

  private Integer checkInterval;

  private WatchableStatus status;

  private String body;

  private String credentials;

  private String credentialsName;

  private String httpMethod;

  private Date createdAt;

  private Date updatedAt;

  public Watchable(Long id, String name, String url, Integer checkInterval, WatchableStatus status,
  String body, String credentials, String credentialsName, String httpMethod, Date createdAt, Date updatedAt) {
    this.id = id;
    this.name = name;
    this.url = url;
    this.checkInterval = checkInterval;
    this.status = status;
    this.body = body;
    this.credentials = credentials;
    this.credentialsName = credentialsName;
    this.httpMethod = httpMethod;
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

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public String getCredentials() {
    return credentials;
  }

  public void setCredentials(String credentials) {
    this.credentials = credentials;
  }

  public String getCredentialsName() {
    return credentialsName;
  }

  public void setCredentialsName(String credentialsName) {
    this.credentialsName = credentialsName;
  }

  public String getHttpMethod() {
    return httpMethod;
  }

  public void setHttpMethod(String httpMethod) {
    this.httpMethod = httpMethod;
  }
}
