package com.airnz.email.service;

import com.airnz.email.model.Email;
import com.airnz.email.respository.EmailRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final EmailRepository emailRepository;

    @Autowired
    public EmailService(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    public List<Email> getEmailsByUserId(UUID userId) {
        return this.emailRepository.getEmailsByUserId(userId);
    }

}
