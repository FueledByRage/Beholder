package com.beholder.watch.adapters.outbound.application.services;

import com.beholder.watch.usecases.LogUseCases;
import com.beholder.watch.model.Log;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.beholder.watch.repository.LogRepository;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JpaLogService implements LogUseCases {

  private final LogRepository logRepository;

  @Override
  public Log save(Log log) {
    return this.logRepository.save(log);
  }

  @Override
  public Log update(Log log) {
    return this.logRepository.save(log);
  }

  @Override
  public Optional<Log> findById(Long id) {
    return this.logRepository.findById(id);
  }

}
