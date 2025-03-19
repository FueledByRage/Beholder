package com.beholder.watch.adapters.outbound.application.services;

import org.springframework.beans.BeanUtils;

import org.springframework.stereotype.Service;
import com.beholder.watch.usecases.WatchableUseCases;
import com.beholder.watch.usecases.ScheduleUseCase;
import com.beholder.watch.dtos.inboud.CreateWatchable;
import com.beholder.watch.repository.WatchableRepository;
import com.beholder.watch.model.watchable.Watchable;
import com.beholder.watch.model.watchable.WatchableStatus;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import org.springframework.context.ApplicationContext;

import com.beholder.watch.usecases.ScheduleUseCase;
import com.beholder.watch.usecases.WatchUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Service
public class JpaWatchableService implements WatchableUseCases {

  @Autowired
  private final transient ScheduleService scheduleService;
  
  @Autowired
  private final transient WatchableRepository watchableRepository;
  
  @Autowired
  private final transient WatchUseCase watchService;

  public JpaWatchableService(ScheduleService scheduleService, WatchableRepository watchableRepository,
    @Lazy  WatchUseCase watchService) {
    this.scheduleService = scheduleService;
    this.watchableRepository = watchableRepository;
    this.watchService = watchService;
  }

  @Override
  public Watchable startWatch(CreateWatchable createWatchableRequest) {
    Watchable watchable = this.save(createWatchableRequest);

    scheduleService.scheduleTask(() -> this.watchService.watch(watchable),
        createWatchableRequest.getCheckInterval().longValue());

    return watchable;
  }

  @Override
  public Watchable save(CreateWatchable createWatchableRequest) {
    Watchable watchable = new Watchable();

    watchable.setStatus(WatchableStatus.UNKNOWN);

    BeanUtils.copyProperties(createWatchableRequest, watchable);

    return this.watchableRepository.save(watchable);
  }

  @Override
  public Optional<Watchable> findById(Long id) {
    return this.watchableRepository.findById(id);
  }

  @Override
  public void updateWatchableStatus(Long id, WatchableStatus status) {
    this.watchableRepository.updateStatus(id, status);
  }

  @Override
  public List<Watchable> findByPage(int size, int page) {
    return this.watchableRepository.findAll(size, page);
  }

  @Override
  public Optional<Watchable> findByName(String name) {
    return this.watchableRepository.findByName(name);
  }
}
