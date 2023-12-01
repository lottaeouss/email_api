package com.airnz.email.service;

import static java.time.Instant.now;
import static java.util.UUID.randomUUID;

import com.airnz.email.model.DraftEmail;
import com.airnz.email.respository.DraftEmailRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DraftEmailService {

    private final DraftEmailRepository draftEmailRepository;

    @Autowired
    public DraftEmailService(DraftEmailRepository draftEmailRepository) {
        this.draftEmailRepository = draftEmailRepository;
    }

    public DraftEmail saveDraftEmail(UUID userId, DraftEmail draftEmail) {
        draftEmail.setId(randomUUID());
        draftEmail.setCreatedOn(now());
        draftEmail.setModifiedOn(now());
        return this.draftEmailRepository.saveDraftEmail(userId, draftEmail);
    }
}
