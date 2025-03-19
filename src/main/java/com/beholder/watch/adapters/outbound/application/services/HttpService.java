package com.beholder.watch.adapters.outbound.application.services;

import com.beholder.watch.model.watchable.WatchableStatus;
import java.net.ConnectException;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.HttpClientErrorException;

import com.beholder.watch.dtos.usecases.HttpResponseDetails;
import com.beholder.watch.adapters.inbound.dtos.HttpResponseDetailsDto;

import com.beholder.watch.usecases.HttpUseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HttpService implements HttpUseCase {

  private final RestTemplate restTemplate;

  private final String SERVICE_UNAVAILABLE_MESSAGE = "Service Unavailable";


  @Override
  public HttpResponseDetails getRequest(String url) {
    try {
        long startTime = System.currentTimeMillis();

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        long endTime = System.currentTimeMillis();
        int responseTime = (int) (endTime - startTime);

        return HttpResponseDetailsDto.builder()
            .responseTime(responseTime)
            .watchableStatus(WatchableStatus.UP)
            .responseStatus(response.getStatusCodeValue())
            .errorMessage(response.getStatusCode().getReasonPhrase())
            .build();
      
    } catch (HttpClientErrorException.BadRequest e) {
        return HttpResponseDetailsDto.builder()
            .responseTime(0)
            .watchableStatus(WatchableStatus.DOWN)
            .responseStatus(HttpStatus.BAD_REQUEST.value())
            .errorMessage(e.getResponseBodyAsString())
            .build();
      
    } catch (HttpClientErrorException.Unauthorized e) {
        return HttpResponseDetailsDto.builder()
            .responseTime(0)
            .watchableStatus(WatchableStatus.DOWN)
            .responseStatus(HttpStatus.UNAUTHORIZED.value())
            .errorMessage(e.getResponseBodyAsString())
            .build();
      
    } catch (ResourceAccessException e) {
        return HttpResponseDetailsDto.builder()
            .responseTime(0)
            .watchableStatus(WatchableStatus.DOWN)
            .responseStatus(HttpStatus.SERVICE_UNAVAILABLE.value())
            .errorMessage(SERVICE_UNAVAILABLE_MESSAGE)
            .build();
      
    } catch (Exception e) {
        return HttpResponseDetailsDto.builder()
            .responseTime(0)
            .watchableStatus(WatchableStatus.DOWN)
            .responseStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .errorMessage(e.getMessage())
            .build();
    }
}

}
