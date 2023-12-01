package com.airnz.email.service;

import static java.time.Instant.now;
import static java.util.UUID.randomUUID;

import com.airnz.email.model.Email;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class EmailServerService {

    public Email sendEmail(UUID userId, Email email) {
        email.setId(randomUUID());
        email.setCreatedOn(now());
        email.setModifiedOn(now());
        System.out.println("email sent, userId=" + userId);

        return email;
    }
}
