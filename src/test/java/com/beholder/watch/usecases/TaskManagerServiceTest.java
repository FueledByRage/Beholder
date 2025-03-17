package com.beholder.watch.adapters.outbound.application.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.ExecutorService;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class TaskManagerServiceTest {

  @Mock
  private ExecutorService cluster;

  @InjectMocks
  private TaskManagerService taskManagerService;

  private Runnable TASK1;

  @BeforeEach
  public void setUp() {
    TASK1 = mock(Runnable.class);
  }

  @Test
  public void shouldSendTaskToClusterSuccessfully() {
    taskManagerService.sendToCluster(TASK1);

    verify(cluster, times(1)).submit(TASK1);
  }

  @Test
  public void shouldStopClusterSuccessfully() {
    taskManagerService.stop();

    verify(cluster, times(1)).shutdown();
  }
}
