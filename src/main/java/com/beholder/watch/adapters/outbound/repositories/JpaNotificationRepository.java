package com.beholder.watch.adapters.outbound.repositories;

import com.beholder.watch.adapters.outbound.entities.JpaEntityNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JpaNotificationRepository extends JpaRepository<JpaEntityNotification, Long> {
  Page<JpaEntityNotification> findAll(Pageable pageable);
}
