package com.jobApplicationTracker.controller;

import com.jobApplicationTracker.DTO.ApplicationTrackerDTO;
import com.jobApplicationTracker.service.ApplicationTrackerService;
import com.jobApplicationTracker.utility.FollowUpMailSender;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Job-Application-Tracker")
public class ApplicationTrackerController {
    @Autowired
    private ApplicationTrackerService applicationTrackerService;
    @Autowired
    private FollowUpMailSender followUpMailSender;

    @GetMapping("/Welcome")
    public ResponseEntity<String> welcome(){
        return new ResponseEntity<>("Hello world form Job-Application-Tracker", HttpStatus.OK);
    }

    @PostMapping("/appliedJob")
    public ResponseEntity<String> addDetailstoDb(@RequestBody ApplicationTrackerDTO applicationTrackerDTO) throws MessagingException {
        ApplicationTrackerDTO applicationTrackerDTO1 = applicationTrackerService.autoFillFollowUpDates(applicationTrackerDTO);
        String s = applicationTrackerService.addDetailstoDb(applicationTrackerDTO1);
        followUpMailSender.welcomeHtmlMail(applicationTrackerDTO1);
        return new ResponseEntity<>(s, HttpStatus.CREATED);
    }

    @GetMapping("/WelcomeMail")
    public ResponseEntity<String> sendMail(){
        String s = followUpMailSender.welcomeMail();
        return new ResponseEntity<>(s, HttpStatus.OK);
    }
}
