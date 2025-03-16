package com.beholder.watch.adapters.outbound.repositories;

import com.beholder.watch.adapters.outbound.entities.JpaEntityWatchable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JpaWatchableRepository extends JpaRepository<JpaEntityWatchable, Long> {
  Page<JpaEntityWatchable> findAll(Pageable pageable);
}
