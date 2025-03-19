package com.beholder.watch.adapters.inbound.dtos;


import java.util.Date;

import com.beholder.watch.model.watchable.Watchable;
import com.beholder.watch.dtos.outbound.NotificationOutput;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationOutputDto implements NotificationOutput {
    private Long id;
    private Watchable watchable;
    private String message;
    private Date sentAt;
}
