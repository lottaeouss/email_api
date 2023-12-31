package com.airnz.email.service;

import static java.time.Instant.now;
import static java.util.UUID.randomUUID;

import com.airnz.email.model.Email;
import com.airnz.email.respository.EmailRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final EmailRepository emailRepository;
    private final EmailServerService emailServerService;

    @Autowired
    public EmailService(EmailRepository emailRepository, EmailServerService emailServerService) {
        this.emailRepository = emailRepository;
        this.emailServerService = emailServerService;
    }

    public Email getEmailByIdAndUserId(UUID userId, UUID emailId) {
        return this.emailRepository.getEmailByIdAndUserId(userId, emailId);
    }

    public Email sendEmail(UUID userId, Email email) {
        email.setId(randomUUID());
        email.setCreatedOn(now());
        email.setModifiedOn(now());
        return this.emailServerService.sendEmail(userId, email);
    }


}
