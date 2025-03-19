package com.beholder.watch.adapters.inbound.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.beholder.watch.adapters.inbound.dtos.CreateNotificationDto;
import com.beholder.watch.adapters.inbound.dtos.NotificationOutputDto;

import com.beholder.watch.model.Notification;

import com.beholder.watch.usecases.NotificationUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("notifications")
@RequiredArgsConstructor
public class NotificationController {
  private final NotificationUseCase service;

  @PostMapping()
  public ResponseEntity<NotificationOutputDto> sendNotification(
    @Valid @RequestBody CreateNotificationDto request) {
    Notification notification = service.save(request);

    NotificationOutputDto notificationOutput = NotificationOutputDto.builder()
      .id(notification.getId())
      .message(notification.getMessage())
      .watchable(notification.getWatchable())
      .sentAt(notification.getSentAt())
      .build();

    return new ResponseEntity<NotificationOutputDto>(notificationOutput, HttpStatus.CREATED);
  }
}
