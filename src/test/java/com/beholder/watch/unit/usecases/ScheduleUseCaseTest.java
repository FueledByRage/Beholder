package com.beholder.watch.adapters.outbound.application.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.beholder.watch.adapters.outbound.application.services.ScheduleService;
import static org.mockito.Mockito.times;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ScheduleUseCaseTest {

  @Mock
  private TaskScheduler taskScheduler;

  @InjectMocks
  private ScheduleService scheduleService;

  private Runnable TASK1;
  private Runnable TASK2;

  @BeforeEach
  public void setUp() {
    TASK1 = mock(Runnable.class);
    TASK2 = mock(Runnable.class);
  }

  @Test
  public void shouldScheduleTaskSuccessfully() {
    scheduleService.scheduleTask(TASK1, 1000L);

    verify(taskScheduler, times(1)).scheduleAtFixedRate(TASK1, 1000L);
  }

  @Test
  public void shouldScheduleMultipleTasksSuccessfully() {
    List<Runnable> tasks = Arrays.asList(TASK1, TASK2);
    scheduleService.scheduleTasks(tasks, 2000L);

    verify(taskScheduler, times(1)).scheduleAtFixedRate(TASK1, 2000L);
    verify(taskScheduler, times(1)).scheduleAtFixedRate(TASK2, 2000L);
  }
}
