package com.beholder.watch.adapters.inbound.dtos;

import com.beholder.watch.dtos.usecases.HttpResponseDetails;

import com.beholder.watch.model.watchable.WatchableStatus;
import lombok.Data;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HttpResponseDetailsDto implements HttpResponseDetails {
  private long responseTime;

  private int responseStatus;

  private String errorMessage;

  private WatchableStatus watchableStatus;
}
