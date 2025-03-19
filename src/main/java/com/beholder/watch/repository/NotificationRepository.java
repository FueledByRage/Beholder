package com.beholder.watch.repository;

import com.beholder.watch.model.Notification;
import java.util.List;
import java.util.Optional;


public interface NotificationRepository {
  public Notification save(Notification notification);
  public Optional<Notification> findById(Long id);
  public List<Notification> findByPage(int size, int page);
}
