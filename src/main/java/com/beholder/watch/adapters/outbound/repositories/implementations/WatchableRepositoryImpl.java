package com.beholder.watch.adapters.outbound.repositories.implementations;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.beholder.watch.adapters.outbound.entities.JpaEntityWatchable;
import com.beholder.watch.model.watchable.Watchable;
import com.beholder.watch.repository.WatchableRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

import com.beholder.watch.adapters.outbound.repositories.JpaWatchableRepository;

@Repository
public class WatchableRepositoryImpl implements WatchableRepository {

  private final JpaWatchableRepository jpaWatchableRepository;

  public WatchableRepositoryImpl(JpaWatchableRepository jpaWatchableRepository) {
    this.jpaWatchableRepository = jpaWatchableRepository;
  }

  @Override
  public Watchable save(Watchable watchable) {
    JpaEntityWatchable jpaEntityWatchable = new JpaEntityWatchable(watchable);

    JpaEntityWatchable savedEntity = jpaWatchableRepository.save(jpaEntityWatchable);

    return new Watchable(savedEntity.getId(), savedEntity.getName(), savedEntity.getUrl(),
        savedEntity.getCheckInterval(), savedEntity.getStatus(), savedEntity.getCreatedAt(),
        savedEntity.getUpdatedAt());
  }

  @Override
  public Optional<Watchable> findById(Long id) {
    Optional<JpaEntityWatchable> jpaEntityWatchable = jpaWatchableRepository.findById(id).map(Optional::of)
        .orElseGet(Optional::empty);

    return jpaEntityWatchable.map(entity -> new Watchable(entity.getId(), entity.getName(), entity.getUrl(),
        entity.getCheckInterval(), entity.getStatus(), entity.getCreatedAt(), entity.getUpdatedAt()));
  }

  @Override
  public List<Watchable> findAll(int size, int page) {
    Pageable pageable = PageRequest.of(page, size);
    Page<JpaEntityWatchable> jpaEntityWatchables = jpaWatchableRepository.findAll(pageable);
    return jpaEntityWatchables.getContent().stream().map(entity -> new Watchable(entity.getId(), entity.getName(),
        entity.getUrl(), entity.getCheckInterval(), entity.getStatus(), entity.getCreatedAt(), entity.getUpdatedAt()))
        .collect(Collectors.toList());
  }
}
