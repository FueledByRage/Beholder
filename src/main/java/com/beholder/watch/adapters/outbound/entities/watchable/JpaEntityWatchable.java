package com.beholder.watch.adapters.outbound.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Index;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Column;

import com.beholder.watch.model.watchable.WatchableStatus;
import com.beholder.watch.model.watchable.Watchable;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "watchables",
  indexes= {
    @Index(name = "idx_watchables_name", columnList = "name", unique = true),
    @Index(name = "idx_watchables_url", columnList = "url", unique = true),
  }
)
public class JpaEntityWatchable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  private String name;

  private String url;

  private Integer checkInterval;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", columnDefinition = "watchablestatus")
  private WatchableStatus status;

  @Column(name = "body")
  private String body;

  @Column(name = "credentials")
  private String credentials;

  @Column(name = "credentials_name")
  private String credentialsName;

  @Column(name = "httpmethod")
  private String httpMethod;

  @CreationTimestamp
  private Date createdAt;

  @UpdateTimestamp
  private Date updatedAt;

  public JpaEntityWatchable(Watchable watchable) {
    this.id = watchable.getId();
    this.name = watchable.getName();
    this.url = watchable.getUrl();
    this.checkInterval = watchable.getCheckInterval();
    this.status = watchable.getStatus();
    this.createdAt = watchable.getCreatedAt();
    this.updatedAt = watchable.getUpdatedAt();
    this.body = watchable.getBody();
    this.credentials = watchable.getCredentials();
    this.credentialsName = watchable.getCredentialsName();
    this.httpMethod = watchable.getHttpMethod();
  }

}
