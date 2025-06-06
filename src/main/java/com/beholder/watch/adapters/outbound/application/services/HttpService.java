package com.beholder.watch.adapters.outbound.application.services;

import com.beholder.watch.dtos.usecases.HttpRequestDetails;
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
  public HttpResponseDetails getRequest(HttpRequestDetails request) {
      HttpHeaders headers = this.createHeaders(request.getCredentials(), request.getCredentialsName());

      HttpEntity<String> entity = new HttpEntity<>(headers);

      return executeRequest(request.getUrl(), HttpMethod.GET, entity);
    }

    @Override
    public HttpResponseDetails postRequest(HttpRequestDetails request) {
        HttpHeaders headers = this.createHeaders(request.getCredentials(), request.getCredentialsName());

        HttpEntity<String> entity = new HttpEntity<>(request.getBody(), headers);

        return executeRequest(request.getUrl(), HttpMethod.POST, entity);
    }

    private HttpHeaders createHeaders(String credentials, String credentialsName) {
        if(credentials == null || credentialsName == null) {
            return new HttpHeaders();
        }
        
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(credentials, credentialsName);
        return headers;
    }

    private HttpResponseDetails executeRequest(String url, HttpMethod method, HttpEntity entity) {
        try {
            long startTime = System.currentTimeMillis();

            ResponseEntity<String> response = restTemplate.exchange(url, method, entity, String.class);

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
