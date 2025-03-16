package com.beholder.watch.repository;

import java.util.List;
import java.util.Optional;

import com.beholder.watch.model.Log;

public interface LogRepository {
  Log save(Log log);

  Optional<Log> findById(Long id);
}
