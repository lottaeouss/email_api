package com.airnz.email.rest.controller;

import com.airnz.email.model.Email;
import com.airnz.email.model.OnCreate;
import com.airnz.email.service.EmailService;
import com.airnz.email.service.UserService;
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

    private final UserService userService;

    @Autowired
    public EmailController(EmailService emailService, UserService userService) {
        this.emailService = emailService;
        this.userService = userService;
    }


    @GetMapping("/users/{userId}/emails/{emailId}")
    @ResponseStatus(HttpStatus.OK)
    public Email getEmailsByIdAndUserId(@PathVariable(name = "emailId") UUID emailId,
        @PathVariable(name = "userId") UUID userId) {
        this.userService.checkUserExists(userId);
        return emailService.getEmailByIdAndUserId(userId, emailId);
    }

    @PostMapping("/users/{userId}/send-email")
    @ResponseStatus(HttpStatus.CREATED)
    public Email sendEmail(@PathVariable(name = "userId") UUID userId,
        @Validated(OnCreate.class) @RequestBody Email email) {
        this.userService.checkUserExists(userId);
        return emailService.sendEmail(userId, email);
    }

}
