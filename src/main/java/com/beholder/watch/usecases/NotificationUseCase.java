package com.beholder.watch.usecases;

import com.beholder.watch.dtos.inbound.CreateNotification;
import java.util.List;
import java.util.Optional;

import com.beholder.watch.model.Notification;

public interface NotificationUseCase {
  public Notification save(CreateNotification createNotificationRequest);
  public Optional<Notification> findById(Long id);
  public List<Notification> findByPage(int size, int page);
}
