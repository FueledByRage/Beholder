package com.beholder.watch.adapters.outbound.application.services;

import com.beholder.watch.adapters.inbound.dtos.HttpRequestDetailsDto;
import com.beholder.watch.model.watchable.WatchableStatus;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.ArgumentMatchers.eq;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.http.HttpHeaders;

import com.beholder.watch.dtos.usecases.HttpRequestDetails;
import com.beholder.watch.adapters.outbound.application.services.HttpService;
import com.beholder.watch.dtos.usecases.HttpResponseDetails;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
public class HttpUseCaseTest {
  
  @InjectMocks
  private HttpService httpService;

  private final String SERVICE_UNAVAILABLE_MESSAGE = "Service Unavailable";
  private final String TEST_URL = "http://www.test.com";
  HttpRequestDetails REQUEST = HttpRequestDetailsDto.builder()
      .url(TEST_URL)
      .body("test")
      .credentials("1234")
      .credentialsName("password")
      .build();
  private final HttpHeaders headers = new HttpHeaders();

  @Mock
  private RestTemplate restTemplate;

  @Before
  public void setUp() {
    headers.setBasicAuth(REQUEST.getCredentials(), REQUEST.getCredentialsName());
    initMocks(this);
  }

  @Test
  public void shouldCallTheEndpoint() {
    when(restTemplate.exchange(eq(TEST_URL), eq(HttpMethod.GET),  any(HttpEntity.class), eq(String.class))).thenReturn(new ResponseEntity<>("", HttpStatus.OK));

    httpService.getRequest(REQUEST);

    verify(restTemplate).exchange(eq(TEST_URL),eq(HttpMethod.GET),  any(HttpEntity.class), eq(String.class));
  }

  @Test
  public void shouldMeasureResponseTimeCorrectly() {
    ResponseEntity<String> mockResponse = new ResponseEntity<>("OK", HttpStatus.OK);

    when(restTemplate.exchange(eq(TEST_URL), eq(HttpMethod.GET), any(HttpEntity.class), eq(String.class)))
        .thenAnswer(invocation -> {
          Thread.sleep(100);
          return mockResponse;
        });

    HttpResponseDetails response = httpService.getRequest(REQUEST);

    assertTrue(response.getResponseTime() >= 100);
  }

  @Test
  public void shouldHandleServiceUnavailability() {
    when(restTemplate.exchange(eq(TEST_URL), eq(HttpMethod.GET), any(HttpEntity.class), eq(String.class)))
        .thenThrow(new ResourceAccessException(SERVICE_UNAVAILABLE_MESSAGE));

    HttpResponseDetails response = httpService.getRequest(REQUEST);

    assertEquals(WatchableStatus.DOWN, response.getWatchableStatus());
    assertEquals(HttpStatus.SERVICE_UNAVAILABLE.value(), response.getResponseStatus());
    assertEquals(SERVICE_UNAVAILABLE_MESSAGE, response.getErrorMessage());
  }


}
