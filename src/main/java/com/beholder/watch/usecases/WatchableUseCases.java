package com.beholder.watch.usecases;

import com.beholder.watch.model.watchable.Watchable;
import com.beholder.watch.model.watchable.WatchableStatus;
import com.beholder.watch.dtos.inboud.CreateWatchable;

import java.util.List;
import java.util.Optional;

public interface WatchableUseCases {

  public Watchable startWatch(CreateWatchable watchable);

  public Watchable save(CreateWatchable watchable);

  public Watchable update(Long id, CreateWatchable watchable);

  public Optional<Watchable> findById(Long id);

  public List<Watchable> findByPage(int size, int page);

  public void updateWatchableStatus(Watchable watchable, WatchableStatus status);
}
