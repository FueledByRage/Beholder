package com.beholder.watch.adapters.outbound.repositories.implementations;

import com.beholder.watch.adapters.utils.mappers.WatchableMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import com.beholder.watch.model.Log;
import com.beholder.watch.model.watchable.Watchable;
import com.beholder.watch.repository.LogRepository;
import com.beholder.watch.adapters.outbound.entities.JpaEntityLog;

import com.beholder.watch.adapters.outbound.repositories.JpaLogRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Repository
public class LogRepositoryImpl implements LogRepository {

  private final JpaLogRepository jpaLogRepository;

  private final WatchableMapper watchableMapper;

  public LogRepositoryImpl(@Lazy JpaLogRepository jpaLogRepository, WatchableMapper watchableMapper) {
    this.jpaLogRepository = jpaLogRepository;
    this.watchableMapper = watchableMapper;
  }

  @Override
  public Log save(Log log) {
    JpaEntityLog jpaEntityLog = new JpaEntityLog(log);

    JpaEntityLog savedEntity = jpaLogRepository.save(jpaEntityLog);

    Watchable watchable = watchableMapper.mapToWatchable(savedEntity.getWatchable());

    return new Log(savedEntity.getId(), watchable, savedEntity.getResponseTime(),
        savedEntity.getResponseStatus(), savedEntity.getErrorMessage(), savedEntity.getCreatedAt(),
        savedEntity.getUpdatedAt());
  }

  @Override
  public Optional<Log> findById(Long id) {
    Optional<JpaEntityLog> jpaEntityLog = jpaLogRepository.findById(id).map(Optional::of).orElseGet(Optional::empty);

    return jpaEntityLog.map(entity -> {
      Watchable watchable = watchableMapper.mapToWatchable(entity.getWatchable());

      return new Log(entity.getId(), watchable, entity.getResponseTime(), entity.getResponseStatus(),
          entity.getErrorMessage(), entity.getCreatedAt(), entity.getUpdatedAt());
    });
  }
}
