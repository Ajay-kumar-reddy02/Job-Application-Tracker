package com.jobApplicationTracker.service;

import com.jobApplicationTracker.DTO.ApplicationTrackerDTO;
import com.jobApplicationTracker.entity.ApplicationTrackerEntity;
import com.jobApplicationTracker.repository.ApplicationTrackerRepository;
import com.jobApplicationTracker.utility.ExcelHelper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class ApplicationTrackerService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ApplicationTrackerRepository applicationTrackerRepository;

    public ApplicationTrackerDTO autoFillFollowUpDates(ApplicationTrackerDTO applicationTrackerDTO){
        LocalDate appliedDate = applicationTrackerDTO.getAppliedDate();
        log.info("Applied from autoFillFollowUpDate " + appliedDate);
        applicationTrackerDTO.setFirstFollowUp(appliedDate.plusDays(7));
        applicationTrackerDTO.setSecondFollowUp(appliedDate.plusDays(10));
        return applicationTrackerDTO;
    }

    public String addDetailstoDb(ApplicationTrackerDTO applicationTrackerDTO){
        ApplicationTrackerEntity map = modelMapper.map(applicationTrackerDTO, ApplicationTrackerEntity.class);
        applicationTrackerRepository.save(map);
        return "Successfully Added";
    }

    public ByteArrayInputStream getJobDetailsInExcel() throws IOException {
        List<ApplicationTrackerEntity> all = applicationTrackerRepository.findAll();
        return ExcelHelper.getJobDetailsInExcel(all);
    }
}
