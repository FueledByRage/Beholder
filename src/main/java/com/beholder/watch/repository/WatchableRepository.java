package com.beholder.watch.repository;

import com.beholder.watch.model.watchable.WatchableStatus;
import java.util.List;
import java.util.Optional;

import com.beholder.watch.model.watchable.Watchable;

public interface WatchableRepository {

  Watchable save(Watchable watchable);

  Optional<Watchable> findById(Long id);

  List<Watchable> findAll(int page, int size);

  int updateStatus(Long id, WatchableStatus status);

  Optional<Watchable> findByName(String name);
}
