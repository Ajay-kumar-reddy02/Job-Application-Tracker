package com.jobApplicationTracker.utility;

import com.jobApplicationTracker.DTO.ApplicationTrackerDTO;
import com.jobApplicationTracker.entity.ApplicationTrackerEntity;
import com.jobApplicationTracker.repository.ApplicationTrackerRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
public class FollowUpMailSender {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private SpringTemplateEngine springTemplateEngine;
    @Autowired
    private ApplicationTrackerRepository applicationTrackerRepository;
    //WelcomeMail
    public String welcomeMail(){
        SimpleMailMessage s = new SimpleMailMessage();
        s.setTo("ajaykumar.kondreddy02@gmail.com");
        s.setSubject("Welcome to Job Application Tracker");
        s.setText("Hello Ajay, You have to apply jobs for a role change in career");
        javaMailSender.send(s);
        return "Mail Sent Successfully";
    }
    //welcome HTML mail
    public String welcomeHtmlMail(ApplicationTrackerDTO applicationTrackerDTO) throws MessagingException {
        MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage,true);
        mimeMessageHelper.setTo("15691a03b3@gmail.com");
        mimeMessageHelper.setSubject(applicationTrackerDTO.getCompanyAppliedTo() +" Job Applied Successfully");
        log.info("Applied to {}", applicationTrackerDTO.getCompanyAppliedTo());
        String emailContent = buildEmailBody(applicationTrackerDTO);
        mimeMessageHelper.setText(emailContent, true);
        javaMailSender.send(mimeMailMessage);
        return "Mail Sent Successfully";
    }
    //Welcome HTML Mail body
    public String buildEmailBody(ApplicationTrackerDTO applicationTrackerDTO){
        Context context = new Context();
        context.setVariable("company", applicationTrackerDTO.getCompanyAppliedTo());
        return springTemplateEngine.process("welcome", context);
    }
    //First Follow Up
    //@Scheduled(initialDelay = 300000, fixedRate = 300000)
    //@Scheduled(cron = "0 0 9 * * ?") //runs daily morning 9 AM
    public void checkFirstFollowUp(){
        log.info("First Follow Up");
        LocalDate today = LocalDate.now();
        log.info("Today date is {}", today);
        LocalDate check = LocalDate.of(2024, 11, 12);
        log.info("First follow up date is {}", check.plusDays(7));
        List<ApplicationTrackerEntity> byFirstFollowUp = applicationTrackerRepository.findByFirstFollowUp(today);
        byFirstFollowUp.forEach(application -> {
            UpdateStatus updateFromCompany = application.getUpdateFromCompany();
            log.info("The Update from the {} is {}", application.getCompanyAppliedTo(), application.getUpdateFromCompany());
            if(updateFromCompany.name().equals("No")){
                try {
                    sendMail(application, application.getCompanyAppliedTo() + " First Follow Message");
                }catch(MessagingException e){
                    e.printStackTrace();
                }
            }
        });
    }
    //First Follow Up sendMail
    public void sendMail(ApplicationTrackerEntity applicationTrackerEntity, String subject) throws MessagingException {
        MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage,true);
        mimeMessageHelper.setTo("ajaykumar.kondreddy02@gmail.com");
        mimeMessageHelper.setSubject(applicationTrackerEntity.getCompanyAppliedTo() +" " + subject);
        log.info("First follow up to {}", applicationTrackerEntity.getCompanyAppliedTo());
        String emailContent = sendMailBody(applicationTrackerEntity);
        mimeMessageHelper.setText(emailContent, true);
        javaMailSender.send(mimeMailMessage);
    }
    //First Follow Up sendMailBody
    public String sendMailBody(ApplicationTrackerEntity applicationTrackerEntity){
        Context context = new Context();
        context.setVariable("company", applicationTrackerEntity.getCompanyAppliedTo());
        return springTemplateEngine.process("firstFollowUp", context);
    }

    //Second Follow Up
    //@Scheduled(initialDelay = 300000, fixedRate = 300000)
    //@Scheduled(cron = "0 0 9 * * ?") //runs daily morning 9 AM
    public void checkSecondFollowUp(){
        log.info("Second Follow Up");
        LocalDate today = LocalDate.now();
        log.info("Today date is {}", today);
        log.info("Second follow up date is {}", today.plusDays(10));
        List<ApplicationTrackerEntity> byFirstFollowUp = applicationTrackerRepository.findBySecondFollowUp(today);
        byFirstFollowUp.forEach(application -> {
            UpdateStatus updateFromCompany = application.getUpdateFromCompany();
            log.info("The Update from the {} is {}", application.getCompanyAppliedTo(), application.getUpdateFromCompany());
            if(updateFromCompany.name().equals("No")){
                try {
                    secondFollowUpsendMail(application, application.getCompanyAppliedTo() + " First Follow Message");
                }catch(MessagingException e){
                    e.printStackTrace();;
                }
            }
        });
    }

    //Second Follow Up sendMail
    public void secondFollowUpsendMail(ApplicationTrackerEntity applicationTrackerEntity, String subject) throws MessagingException {
        MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage,true);
        mimeMessageHelper.setTo("ajaykumar.kondreddy02@gmail.com");
        mimeMessageHelper.setSubject(applicationTrackerEntity.getCompanyAppliedTo() +" " + subject);
        log.info("Second follow up to {}", applicationTrackerEntity.getCompanyAppliedTo());
        String emailContent = secondFollowUpSendMailBody(applicationTrackerEntity);
        mimeMessageHelper.setText(emailContent, true);
        javaMailSender.send(mimeMailMessage);
    }
    //Second Follow Up sendMailBody
    public String secondFollowUpSendMailBody(ApplicationTrackerEntity applicationTrackerEntity){
        Context context = new Context();
        context.setVariable("company", applicationTrackerEntity.getCompanyAppliedTo());
        return springTemplateEngine.process("secondFollowUp", context);
    }




}
