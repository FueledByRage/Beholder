package com.beholder.watch.adapters.outbound.application.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


import com.beholder.watch.adapters.outbound.application.services.JpaLogService;
import com.beholder.watch.model.Log;
import com.beholder.watch.repository.LogRepository;

@ExtendWith(SpringExtension.class)
public class LogUseCaseTest {

  @Mock
  private LogRepository logRepository;

  @InjectMocks
  private JpaLogService jpaLogService;

  private Log SAMPLE_LOG;

  @BeforeEach
  public void setUp() {
    SAMPLE_LOG = new Log();
    SAMPLE_LOG.setId(1L);
  }

  @Test
  public void shouldSaveLogSuccessfully() {
    when(logRepository.save(SAMPLE_LOG)).thenReturn(SAMPLE_LOG);

    Log savedLog = jpaLogService.save(SAMPLE_LOG);

    assertEquals(SAMPLE_LOG.getId(), savedLog.getId());
    verify(logRepository, times(1)).save(SAMPLE_LOG);
  }

  @Test
  public void shouldFindLogByIdSuccessfully() {
    when(logRepository.findById(1L)).thenReturn(Optional.of(SAMPLE_LOG));

    Optional<Log> foundLog = jpaLogService.findById(1L);

    assertTrue(foundLog.isPresent());
    verify(logRepository, times(1)).findById(1L);
  }

  @Test
  public void shouldReturnEmptyWhenLogNotFound() {
    when(logRepository.findById(2L)).thenReturn(Optional.empty());

    Optional<Log> foundLog = jpaLogService.findById(2L);

    assertFalse(foundLog.isPresent());
    verify(logRepository, times(1)).findById(2L);
  }
}
