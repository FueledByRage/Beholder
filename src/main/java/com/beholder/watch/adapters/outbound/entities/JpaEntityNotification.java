package com.beholder.watch.adapters.outbound.entities;

import java.util.Date;

import com.beholder.watch.adapters.outbound.entities.JpaEntityWatchable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notifications")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JpaEntityNotification {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "watchable_id", referencedColumnName = "id", nullable = false)
  private JpaEntityWatchable watchable;

  private String message;

  private Date sentAt;
}