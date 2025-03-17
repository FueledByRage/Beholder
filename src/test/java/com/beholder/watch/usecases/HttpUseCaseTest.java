package com.beholder.watch.adapters.outbound.application.services;

import com.beholder.watch.model.watchable.WatchableStatus;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.web.client.ResourceAccessException;


import com.beholder.watch.adapters.outbound.application.services.HttpService;
import com.beholder.watch.dtos.usecases.HttpResponseDetails;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus; 
import org.springframework.http.HttpHeaders;
@ExtendWith(SpringExtension.class)
public class HttpUseCaseTest {
  
  @InjectMocks
  private HttpService httpService;

  private final String SERVICE_UNAVAILABLE_MESSAGE = "Service Unavailable";
  private final String TEST_URL = "http://www.test.com";
  private final HttpEntity<String> entity = new HttpEntity<>(new HttpHeaders());

  @Mock
  private RestTemplate restTemplate;

  @Before
  public void setUp() {
    initMocks(this);
  }

  @Test
  public void shouldCallTheEndpoint() {
    when(restTemplate.exchange(TEST_URL, HttpMethod.GET, entity, String.class)).thenReturn(new ResponseEntity<>("", HttpStatus.OK));

    httpService.getRequest(TEST_URL);

    verify(restTemplate).exchange(TEST_URL, HttpMethod.GET, entity, String.class);
  }

  @Test
  public void shouldMeasureResponseTimeCorrectly() {
    ResponseEntity<String> mockResponse = new ResponseEntity<>("OK", HttpStatus.OK);

    when(restTemplate.exchange(eq(TEST_URL), eq(HttpMethod.GET), eq(entity), eq(String.class)))
        .thenAnswer(invocation -> {
          Thread.sleep(100);
          return mockResponse;
        });

    HttpResponseDetails response = httpService.getRequest(TEST_URL);

    assertTrue(response.getResponseTime() >= 100, "O tempo de resposta deveria ser >= 100ms");
  }

  @Test
  public void shouldHandleServiceUnavailability() {
    when(restTemplate.exchange(eq(TEST_URL), eq(HttpMethod.GET), eq(entity), eq(String.class)))
        .thenThrow(new ResourceAccessException("Service Unavailable"));

    HttpResponseDetails response = httpService.getRequest(TEST_URL);

    assertEquals(WatchableStatus.DOWN, response.getWatchableStatus());
    assertEquals(HttpStatus.SERVICE_UNAVAILABLE.value(), response.getResponseStatus());
    assertEquals("Service Unavailable", response.getErrorMessage());
  }


}
