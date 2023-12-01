package com.airnz.email.service;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.airnz.email.model.DraftEmail;
import com.airnz.email.respository.DraftEmailRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {DraftEmailService.class}, webEnvironment = WebEnvironment.NONE)
class DraftEmailServiceTest {

    private DraftEmailService draftEmailService;

    @MockBean
    private DraftEmailRepository draftEmailRepository;

    @BeforeEach
    void setup() {
        draftEmailService = new DraftEmailService(draftEmailRepository);
    }

    @Test
    public void testSaveDraftEmail() {
        DraftEmail draftEmail = new DraftEmail();
        draftEmail.setSender("test_sender_one@test.com");
        draftEmail.setRecipients(List.of("test_recipient@test.com"));
        draftEmail.setCcRecipients(List.of("test_cc_recipient@test.com"));
        draftEmail.setBccRecipients(List.of("test_bcc_recipient@test.com"));
        draftEmail.setAttachments(List.of("TEST_ATTACHMENT.jpg", "TEST_ATTACHMENT.txt"));
        draftEmail.setSubject("test email subject one");
        draftEmail.setBody("test email body one");

        DraftEmail savedDraftEmail = new DraftEmail();
        savedDraftEmail.setSender(draftEmail.getSender());
        savedDraftEmail.setRecipients(draftEmail.getRecipients());
        savedDraftEmail.setCcRecipients(draftEmail.getCcRecipients());
        savedDraftEmail.setBccRecipients(draftEmail.getBccRecipients());
        savedDraftEmail.setAttachments(draftEmail.getAttachments());
        savedDraftEmail.setSubject(draftEmail.getSubject());
        savedDraftEmail.setBody(draftEmail.getBody());
        savedDraftEmail.setId(randomUUID());

        when(draftEmailRepository.saveDraftEmail(any(UUID.class), any(DraftEmail.class))).thenReturn(savedDraftEmail);
        DraftEmail actual = draftEmailService.saveDraftEmail(randomUUID(), draftEmail);
        verify(draftEmailRepository).saveDraftEmail(any(UUID.class), any(DraftEmail.class));
        assertThat(actual).isEqualTo(savedDraftEmail);
    }

}
