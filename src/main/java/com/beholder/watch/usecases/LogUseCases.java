package com.beholder.watch.usecases;

import java.util.Optional;

import com.beholder.watch.model.Log;

public interface LogUseCases {

  public Log save(Log log);

  public Optional<Log> findById(Long id);

}