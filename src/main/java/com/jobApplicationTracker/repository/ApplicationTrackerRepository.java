package com.jobApplicationTracker.repository;

import com.jobApplicationTracker.entity.ApplicationTrackerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ApplicationTrackerRepository extends JpaRepository<ApplicationTrackerEntity, Integer> {
    List<ApplicationTrackerEntity> findByFirstFollowUp(LocalDate localDate);
    List<ApplicationTrackerEntity> findBySecondFollowUp(LocalDate localDate);
}
