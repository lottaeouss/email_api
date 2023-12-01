package com.airnz.email.service;

import static java.time.Instant.now;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.airnz.email.model.Email;
import com.airnz.email.respository.EmailRepository;
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
@SpringBootTest(classes = {EmailService.class}, webEnvironment = WebEnvironment.NONE)
class EmailServiceTest {

    private EmailService emailService;

    @MockBean
    private EmailRepository emailRepository;

    @MockBean
    private EmailServerService emailServerService;

    @BeforeEach
    void setup() {
        emailService = new EmailService(emailRepository, emailServerService);
    }

    @Test
    public void testGetAllEmails() {
        Email defaultEmail = new Email();
        defaultEmail.setId(randomUUID());
        defaultEmail.setSender("test_sender_one@test.com");
        defaultEmail.setRecipients(List.of("test_recipient@test.com"));
        defaultEmail.setSubject("test email subject");
        defaultEmail.setBody("test email body");
        defaultEmail.setCreatedOn(now());
        defaultEmail.setModifiedOn(now());

        when(emailRepository.getEmailByIdAndUserId(any(UUID.class), any(UUID.class))).thenReturn(defaultEmail);
        Email actual = emailService.getEmailByIdAndUserId(randomUUID(), randomUUID());
        verify(emailRepository).getEmailByIdAndUserId(any(UUID.class), any(UUID.class));
        assertThat(actual).isEqualTo(defaultEmail);
    }

    @Test
    public void testSendEmail() {
        Email email = new Email();
        email.setSender("test_sender_one@test.com");
        email.setRecipients(List.of("test_recipient@test.com"));
        email.setSubject("test email subject one");
        email.setBody("test email body one");

        Email sentEmail = new Email();
        sentEmail.setSender("test_sender_one@test.com");
        sentEmail.setRecipients(List.of("test_recipient@test.com"));
        sentEmail.setSubject("test email subject one");
        sentEmail.setBody("test email body one");
        sentEmail.setId(randomUUID());
        sentEmail.setCreatedOn(now());
        sentEmail.setModifiedOn(now());

        when(emailServerService.sendEmail(any(UUID.class), any(Email.class))).thenReturn(sentEmail);
        Email actual = emailService.sendEmail(randomUUID(), email);
        verify(emailServerService).sendEmail(any(UUID.class), any(Email.class));
        assertThat(actual).isEqualTo(sentEmail);
    }

}
