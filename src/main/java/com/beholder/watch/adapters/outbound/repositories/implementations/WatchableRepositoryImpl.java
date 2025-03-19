package com.beholder.watch.adapters.outbound.repositories.implementations;

import com.beholder.watch.adapters.utils.mappers.WatchableMapper;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.beholder.watch.adapters.outbound.entities.JpaEntityWatchable;
import com.beholder.watch.model.watchable.Watchable;
import com.beholder.watch.repository.WatchableRepository;

import com.beholder.watch.model.watchable.WatchableStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

import com.beholder.watch.adapters.outbound.repositories.JpaWatchableRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class WatchableRepositoryImpl implements WatchableRepository {

  private final JpaWatchableRepository jpaWatchableRepository;
  private final WatchableMapper watchableMapper;

  @Override
  public Watchable save(Watchable watchable) {
    JpaEntityWatchable jpaEntityWatchable = new JpaEntityWatchable(watchable);

    JpaEntityWatchable savedEntity = jpaWatchableRepository.save(jpaEntityWatchable);

    return watchableMapper.mapToWatchable(savedEntity);
  }

  @Override
  public Optional<Watchable> findById(Long id) {
    Optional<JpaEntityWatchable> jpaEntityWatchable = jpaWatchableRepository.findById(id).map(Optional::of)
        .orElseGet(Optional::empty);

    return jpaEntityWatchable.map(watchableMapper::mapToWatchable);
  }

  @Override
  public int updateStatus(Long id, WatchableStatus status) {
    return jpaWatchableRepository.updateWatchableStatus(id, status);
  }

  @Override
  public List<Watchable> findAll(int size, int page) {
    Pageable pageable = PageRequest.of(page, size);

    Page<JpaEntityWatchable> jpaEntityWatchables = jpaWatchableRepository.findAll(pageable);
    
    return jpaEntityWatchables.getContent().stream().map(watchableMapper::mapToWatchable).collect(Collectors.toList());
  }

  @Override
  public Optional<Watchable> findByName(String name) {
    return this.jpaWatchableRepository.findByName(name).map(watchableMapper::mapToWatchable);
  }
}
