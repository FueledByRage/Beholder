package com.beholder.watch.adapters.outbound.application.services;
 
import com.beholder.watch.adapters.inbound.dtos.CreateWatchableDto;
import com.beholder.watch.model.watchable.Watchable;
import com.beholder.watch.repository.WatchableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Arrays;
import java.util.Optional;

import com.beholder.watch.model.watchable.WatchableStatus;
import com.beholder.watch.dtos.inboud.CreateWatchable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.eq;



@ExtendWith(SpringExtension.class)
public class WatchableServiceTest {

  @Mock
  private ScheduleService scheduleService;

  @Mock
  private WatchableRepository watchableRepository;

  @InjectMocks
  private JpaWatchableService jpaWatchableService;

  private CreateWatchable CREATE_WATCHABLE_REQUEST = CreateWatchableDto.builder().checkInterval(5).build();

  private Watchable WATCHABLE = new Watchable();

  @BeforeEach
  public void setUp() {
    WATCHABLE.setId(1L);
    WATCHABLE.setStatus(WatchableStatus.UNKNOWN);
  }

  @Test
  public void shouldSaveWatchableSuccessfully() {
    Watchable watchable = new Watchable();

    watchable.setCheckInterval(5);
    watchable.setStatus(WatchableStatus.UNKNOWN);

    when(watchableRepository.save(any(Watchable.class))).thenReturn(WATCHABLE);

    jpaWatchableService.save(CREATE_WATCHABLE_REQUEST);

    verify(watchableRepository, times(1)).save(any(Watchable.class));
  }

  @Test
  public void shouldStartWatchSuccessfully() {
    when(watchableRepository.save(any(Watchable.class))).thenReturn(WATCHABLE);

    Watchable startedWatchable = jpaWatchableService.startWatch(CREATE_WATCHABLE_REQUEST);

    assertEquals(WatchableStatus.UNKNOWN, startedWatchable.getStatus());

    verify(scheduleService, times(1)).scheduleTask(any(Runnable.class), eq(5L));
  }

  @Test
  public void shouldFindByIdSuccessfully() {
    when(watchableRepository.findById(1L)).thenReturn(Optional.of(WATCHABLE));

    Optional<Watchable> foundWatchable = jpaWatchableService.findById(1L);

    assertTrue(foundWatchable.isPresent());
    assertEquals(1L, foundWatchable.get().getId());
    verify(watchableRepository, times(1)).findById(1L);
  }

  @Test
  public void shouldReturnEmptyWhenWatchableNotFound() {
    when(watchableRepository.findById(2L)).thenReturn(Optional.empty());

    Optional<Watchable> foundWatchable = jpaWatchableService.findById(2L);

    assertFalse(foundWatchable.isPresent());
    verify(watchableRepository, times(1)).findById(2L);
  }

  @Test
  public void shouldUpdateWatchableStatusSuccessfully() {
    jpaWatchableService.updateWatchableStatus(1L, WatchableStatus.UP);

    verify(watchableRepository, times(1)).updateStatus(1L, WatchableStatus.UP);
  }

  @Test
  public void shouldFindByPageSuccessfully() {
    List<Watchable> watchableList = Arrays.asList(WATCHABLE);
    when(watchableRepository.findAll(10, 1)).thenReturn(watchableList);

    List<Watchable> watchables = jpaWatchableService.findByPage(10, 1);

    assertFalse(watchables.isEmpty());
    assertEquals(1, watchables.size());
    verify(watchableRepository, times(1)).findAll(10, 1);
  }
}
