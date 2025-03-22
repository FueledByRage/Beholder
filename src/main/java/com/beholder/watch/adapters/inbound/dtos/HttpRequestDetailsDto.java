package com.beholder.watch.adapters.inbound.dtos;

import com.beholder.watch.dtos.usecases.HttpRequestDetails;
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
public class HttpRequestDetailsDto  implements HttpRequestDetails {
  private String url;

  private String body;

  private String credentials;

  private String credentialsName;
  
}
