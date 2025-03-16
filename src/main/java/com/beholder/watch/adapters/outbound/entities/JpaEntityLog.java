package com.beholder.watch.adapters.outbound.entities;

import lombok.*;

import com.beholder.watch.model.Log;

import com.beholder.watch.adapters.outbound.entities.JpaEntityWatchable;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "logs")
public class JpaEntityLog {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "watchable_id", referencedColumnName = "id", nullable = false)
  private JpaEntityWatchable watchable;

  private int responseTime;

  private int responseStatus;

  private String errorMessage;

  @CreationTimestamp
  private Date createdAt;

  @UpdateTimestamp
  private Date updatedAt;

  public JpaEntityLog(Log log) {
    this.id = log.getId();
    this.responseTime = log.getResponseTime();
    this.responseStatus = log.getResponseStatus();
    this.errorMessage = log.getErrorMessage();
    this.createdAt = log.getCreatedAt();
    this.updatedAt = log.getUpdatedAt();
    
    if (log.getWatchable() != null) {
      JpaEntityWatchable watchable = new JpaEntityWatchable();
      watchable.setId(log.getWatchable().getId());
      this.watchable = watchable;
    }
  }
}
