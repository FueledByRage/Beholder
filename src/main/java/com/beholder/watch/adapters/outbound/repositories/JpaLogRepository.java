package com.beholder.watch.adapters.outbound.repositories;

import com.beholder.watch.adapters.outbound.entities.JpaEntityLog;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLogRepository extends JpaRepository<JpaEntityLog, Long> {

}
