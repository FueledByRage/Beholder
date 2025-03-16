package com.beholder.watch.adapters.outbound.repositories.implementations;

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

  @Autowired
  @Lazy
  private final JpaLogRepository jpaLogRepository;

  public LogRepositoryImpl(JpaLogRepository jpaLogRepository) {
    this.jpaLogRepository = jpaLogRepository;
  }

  @Override
  public Log save(Log log) {
    JpaEntityLog jpaEntityLog = new JpaEntityLog(log);

    JpaEntityLog savedEntity = jpaLogRepository.save(jpaEntityLog);

    Watchable watchable = new Watchable(savedEntity.getWatchable().getId(), savedEntity.getWatchable().getName(),
        savedEntity.getWatchable().getUrl(), savedEntity.getWatchable().getCheckInterval(),
        savedEntity.getWatchable().getStatus(), savedEntity.getWatchable().getCreatedAt(),
        savedEntity.getWatchable().getUpdatedAt());

    return new Log(savedEntity.getId(), watchable, savedEntity.getResponseTime(),
        savedEntity.getResponseStatus(), savedEntity.getErrorMessage(), savedEntity.getCreatedAt(),
        savedEntity.getUpdatedAt());
  }

  @Override
  public Optional<Log> findById(Long id) {
    Optional<JpaEntityLog> jpaEntityLog = jpaLogRepository.findById(id).map(Optional::of).orElseGet(Optional::empty);

    return jpaEntityLog.map(entity -> {
      Watchable watchable = new Watchable(entity.getWatchable().getId(), entity.getWatchable().getName(),
          entity.getWatchable().getUrl(), entity.getWatchable().getCheckInterval(), entity.getWatchable().getStatus(),
          entity.getWatchable().getCreatedAt(), entity.getWatchable().getUpdatedAt());

      return new Log(entity.getId(), watchable, entity.getResponseTime(), entity.getResponseStatus(),
          entity.getErrorMessage(), entity.getCreatedAt(), entity.getUpdatedAt());
    });
  }
}
