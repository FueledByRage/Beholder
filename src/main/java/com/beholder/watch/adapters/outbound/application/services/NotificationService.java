package com.beholder.watch.adapters.outbound.application.services;

import com.beholder.watch.dtos.inbound.CreateNotification;
import com.beholder.watch.model.Notification;
import com.beholder.watch.model.watchable.Watchable;
import com.beholder.watch.repository.NotificationRepository;
import com.beholder.watch.usecases.NotificationUseCase;
import com.beholder.watch.usecases.WatchableUseCases;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService implements NotificationUseCase {

    private final NotificationRepository notificationRepository;

    private final WatchableUseCases watchableUseCases;

    @Override
    public Notification save(CreateNotification createNotificationRequest) {
      Notification notification = this.buildNotification(createNotificationRequest);

      return notificationRepository.save(notification);
    }

    @Override
    public Optional<Notification> findById(Long id) {
        return notificationRepository.findById(id);
    }

    @Override
    public List<Notification> findByPage(int size, int page) {
        return notificationRepository.findByPage(size, page);
    }

    private Notification buildNotification(CreateNotification createNotificationRequest) {
        Watchable watchable = findWatchable(createNotificationRequest.getWatchableName());
        Date sentAt = parseDate(createNotificationRequest.getSentAt());
        System.out.println(" - NotificationService.buildNotification: watchable=" + watchable + ", sentAt=" + sentAt);
        return new Notification(watchable, createNotificationRequest.getMessage(), sentAt);
    }

    private Watchable findWatchable(String watchableName) {
        return watchableUseCases.findByName(watchableName).orElseThrow(() -> new RuntimeException("Watchable not found"));
    }

    private Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
        } catch (Exception e) {
            throw new RuntimeException("Invalid date format");
        }
    }
}
