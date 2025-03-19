package com.beholder.watch.adapters.inbound.dtos;

import com.beholder.watch.dtos.inbound.CreateNotification;

import lombok.Data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateNotificationDto implements CreateNotification{
    @NotEmpty(message = "WatchableName may not be empty")
    @NotNull(message = "WatchableName may not be null")
    private String watchableName;

    @NotEmpty(message = "Message may not be empty")
    @NotNull(message = "Message may not be null")
    private String message;

    @NotEmpty(message = "SentAt may not be empty")
    @NotNull(message = "SentAt may not be null")
    private String sentAt;
}
