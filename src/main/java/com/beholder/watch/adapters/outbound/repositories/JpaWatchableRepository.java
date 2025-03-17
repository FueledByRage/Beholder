package com.beholder.watch.adapters.outbound.repositories;

import com.beholder.watch.adapters.outbound.entities.JpaEntityWatchable;

import com.beholder.watch.model.watchable.WatchableStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface JpaWatchableRepository extends JpaRepository<JpaEntityWatchable, Long> {
  Page<JpaEntityWatchable> findAll(Pageable pageable);

  @Modifying
  @Transactional
  @Query("UPDATE JpaEntityWatchable w SET w.status = :status WHERE w.id = :id")
  int updateWatchableStatus(Long id, WatchableStatus status);
}
