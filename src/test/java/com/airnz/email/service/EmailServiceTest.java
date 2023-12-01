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
        Email defaultEmailOne = new Email();
        defaultEmailOne.setId(randomUUID());
        defaultEmailOne.setSender("test_sender_one@test.com");
        defaultEmailOne.setRecipients(List.of("test_recipient@test.com"));
        defaultEmailOne.setSubject("test email subject one");
        defaultEmailOne.setBody("test email body one");
        defaultEmailOne.setCreatedOn(now());
        defaultEmailOne.setModifiedOn(now());

        Email defaultEmailTwo = new Email();
        defaultEmailTwo.setId(randomUUID());
        defaultEmailTwo.setSender("test_sender_two@test.com");
        defaultEmailTwo.setRecipients(List.of("test_recipient@test.com"));
        defaultEmailTwo.setSubject("test email subject two");
        defaultEmailTwo.setBody("test email body two");
        defaultEmailTwo.setCreatedOn(now());
        defaultEmailTwo.setModifiedOn(now());

        List<Email> searchResponse = List.of(defaultEmailOne, defaultEmailTwo);

        when(emailRepository.getEmailsByUserId(any(UUID.class))).thenReturn(searchResponse);
        List<Email> actual = emailService.getEmailsByUserId(randomUUID());
        verify(emailRepository).getEmailsByUserId(any(UUID.class));
        assertThat(actual).hasSameSizeAs(searchResponse);
        assertThat(actual).isEqualTo(searchResponse);
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
