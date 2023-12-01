package com.airnz.email.controller;

import com.airnz.email.model.Email;
import com.airnz.email.model.OnCreate;
import com.airnz.email.service.EmailService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${resource.path}")
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }


    @GetMapping("/users/{userId}/emails")
    @ResponseStatus(HttpStatus.OK)
    public List<Email> getEmailsByUserId(@PathVariable(name = "userId") UUID userId) {
        return emailService.getEmailsByUserId(userId);
    }

    @PostMapping("/users/{userId}/send-email")
    @ResponseStatus(HttpStatus.CREATED)
    public Email sendEmail(@PathVariable(name = "userId") UUID userId,
        @Validated(OnCreate.class) @RequestBody Email email) {
        return emailService.sendEmail(userId, email);
    }

}
