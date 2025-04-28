package com.beholder.watch.adapters.outbound.application.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.List;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.beholder.watch.model.Notification;
import com.beholder.watch.model.watchable.Watchable;
import com.beholder.watch.repository.NotificationRepository;
import com.beholder.watch.usecases.WatchableUseCases;
import com.beholder.watch.adapters.inbound.dtos.CreateNotificationDto;

@ExtendWith(SpringExtension.class)
public class NotificationUseCaseTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private WatchableUseCases watchableUseCases;

    @InjectMocks
    private NotificationService notificationService;

    private CreateNotificationDto SAMPLE_NOTIFICATION_REQUEST;
    private Notification SAMPLE_NOTIFICATION;
    private Watchable SAMPLE_WATCHABLE;

    @BeforeEach
    public void setUp() {
        SAMPLE_WATCHABLE = new Watchable();
        SAMPLE_WATCHABLE.setName("TestWatchable");
        
        SAMPLE_NOTIFICATION_REQUEST = CreateNotificationDto.builder()
            .watchableName("TestWatchable")
            .message("Test message")
            .sentAt("2025-03-19 12:00:00")
            .build();
        SAMPLE_NOTIFICATION = new Notification(SAMPLE_WATCHABLE, "Test message", new java.util.Date());
    }

    @Test
    public void shouldSaveNotificationSuccessfully() {
        when(watchableUseCases.findByName("TestWatchable")).thenReturn(Optional.of(SAMPLE_WATCHABLE));
        when(notificationRepository.save(any(Notification.class))).thenReturn(SAMPLE_NOTIFICATION);

        Notification savedNotification = notificationService.save(SAMPLE_NOTIFICATION_REQUEST);

        assertNotNull(savedNotification);
        assertEquals("Test message", savedNotification.getMessage());
        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
    public void shouldFindNotificationByIdSuccessfully() {
        when(notificationRepository.findById(1L)).thenReturn(Optional.of(SAMPLE_NOTIFICATION));

        Optional<Notification> foundNotification = notificationService.findById(1L);

        assertTrue(foundNotification.isPresent());
        verify(notificationRepository, times(1)).findById(1L);
    }

    @Test
    public void shouldReturnEmptyWhenNotificationNotFound() {
        when(notificationRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Notification> foundNotification = notificationService.findById(2L);

        assertFalse(foundNotification.isPresent());
        verify(notificationRepository, times(1)).findById(2L);
    }

    @Test
    public void shouldFindNotificationsByPage() {
        when(notificationRepository.findByPage(10, 0)).thenReturn(Collections.singletonList(SAMPLE_NOTIFICATION));

        List<Notification> notifications = notificationService.findByPage(10, 0);

        assertFalse(notifications.isEmpty());
        assertEquals(1, notifications.size());
        verify(notificationRepository, times(1)).findByPage(10, 0);
    }
}
