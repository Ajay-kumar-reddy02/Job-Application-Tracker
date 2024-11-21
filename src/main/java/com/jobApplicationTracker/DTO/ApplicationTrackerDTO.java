package com.jobApplicationTracker.DTO;

import com.jobApplicationTracker.utility.UpdateStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationTrackerDTO {
    private Integer id;
    private String companyAppliedTo;
    private String jobDesignation;
    private String link;
    private String appliedThrough;
    private String resumeName;
    private LocalDate appliedDate;
    private UpdateStatus updateFromCompany;
    private LocalDate firstFollowUp;
    private LocalDate secondFollowUp;
    private String finalUpdate;
    private String notes;
}
