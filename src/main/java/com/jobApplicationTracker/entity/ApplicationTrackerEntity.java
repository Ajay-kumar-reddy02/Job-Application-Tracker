package com.jobApplicationTracker.entity;


import com.jobApplicationTracker.utility.UpdateStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "application_tracker")
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationTrackerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String companyAppliedTo;
    private String jobDesignation;
    private String link;
    @Column(nullable = false)
    private String appliedThrough;
    @Column(nullable = false)
    private String resumeName;
    @Column(nullable = false)
    private LocalDate appliedDate;
    @Enumerated(EnumType.STRING)
    private UpdateStatus updateFromCompany;
    @Column(nullable = false)
    private LocalDate firstFollowUp;
    @Column(nullable = false)
    private LocalDate secondFollowUp;
    private String finalUpdate;
    private String notes;

}
