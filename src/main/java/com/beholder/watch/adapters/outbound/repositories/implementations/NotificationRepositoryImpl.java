package com.beholder.watch.adapters.outbound.repositories.implementations;

import com.beholder.watch.adapters.outbound.entities.JpaEntityNotification;
import com.beholder.watch.adapters.outbound.entities.JpaEntityWatchable;
import com.beholder.watch.adapters.outbound.repositories.JpaNotificationRepository;
import com.beholder.watch.adapters.utils.mappers.WatchableMapper;
import com.beholder.watch.model.Notification;
import com.beholder.watch.model.watchable.Watchable;
import com.beholder.watch.repository.NotificationRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;


@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {
    private final JpaNotificationRepository jpaNotificationRepository;
    private final WatchableMapper watchableMapper;

    @Override
    public Notification save(Notification notification) {
      JpaEntityWatchable jpaEntityWatchable = watchableMapper.mapToJpaEntityWatchable(notification.getWatchable());
      JpaEntityNotification jpaEntityNotification = JpaEntityNotification.builder().
        message(notification.getMessage())
        .watchable(jpaEntityWatchable)
        .sentAt(notification.getSentAt())
        .build();
      
      JpaEntityNotification savedEntity = jpaNotificationRepository.save(jpaEntityNotification);

      return this.mapToNotification(savedEntity);
    }

    @Override
    public Optional<Notification> findById(Long id) {
      Optional<JpaEntityNotification> jpaEntityNotification = jpaNotificationRepository.findById(id);

      return jpaEntityNotification.map(this::mapToNotification);
    }

    @Override
    public List<Notification> findByPage(int size, int page) {
      Pageable pageable = PageRequest.of(page, size);

      Page<JpaEntityNotification> jpaEntityNotifications = jpaNotificationRepository.findAll(pageable);

      return jpaEntityNotifications.stream().map(this::mapToNotification).collect(Collectors.toList());
    }
    
    private Notification mapToNotification(JpaEntityNotification jpaEntityNotification) {
      Watchable watchable = watchableMapper.mapToWatchable(jpaEntityNotification.getWatchable());

      return new Notification(jpaEntityNotification.getId(), watchable, jpaEntityNotification.getMessage(), jpaEntityNotification.getSentAt());
    }

    
}
