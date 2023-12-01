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

    @BeforeEach
    void setup() {
        emailService = new EmailService(emailRepository);
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

}
