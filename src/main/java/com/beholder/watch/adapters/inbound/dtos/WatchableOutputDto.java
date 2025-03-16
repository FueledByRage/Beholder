package com.beholder.watch.adapters.inbound.dtos;

import com.beholder.watch.model.watchable.WatchableStatus;
import com.beholder.watch.dtos.outbound.WatchableOutput;

import lombok.Data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Min;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WatchableOutputDto implements WatchableOutput {
    private Long id;
    private String name;
    private String url;
    private Integer checkInterval;
    private WatchableStatus status;
}