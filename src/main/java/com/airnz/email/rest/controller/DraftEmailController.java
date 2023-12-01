package com.airnz.email.rest.controller;

import com.airnz.email.model.DraftEmail;
import com.airnz.email.service.DraftEmailService;
import com.airnz.email.service.UserService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${resource.path}")
public class DraftEmailController {

    private final DraftEmailService draftEmailService;

    private final UserService userService;

    @Autowired
    public DraftEmailController(DraftEmailService draftEmailService, UserService userService) {
        this.draftEmailService = draftEmailService;
        this.userService = userService;
    }

    @PostMapping("/users/{userId}/draft-email")
    @ResponseStatus(HttpStatus.CREATED)
    public DraftEmail saveDraftEmail(@PathVariable(name = "userId") UUID userId,
        @RequestBody DraftEmail draftEmail) {
        this.userService.checkUserExists(userId);
        return draftEmailService.saveDraftEmail(userId, draftEmail);
    }
}
