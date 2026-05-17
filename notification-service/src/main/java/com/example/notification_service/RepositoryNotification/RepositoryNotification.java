package com.example.notification_service.RepositoryNotification;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.notification_service.ModelNotification.ModelNotification;

@Repository
public interface RepositoryNotification extends JpaRepository<ModelNotification, Long> {

    List<ModelNotification> findByUserId(Long userId);

    List<ModelNotification> findBySent(Boolean sent);
}