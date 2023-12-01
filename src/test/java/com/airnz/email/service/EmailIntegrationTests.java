package com.airnz.email.service;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.airnz.email.model.Email;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class EmailIntegrationTests {

    @Value("${resource.path}")
    private String basePath;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<Email> response;

    private final UUID defaultUserId = UUID.fromString("e711f133-ab0c-483e-8507-81416745c78e");

    @Test
    void testGetValidEmailForUser() throws Exception {
        UUID defaultEmailIdOne = UUID.fromString("3302f3b6-3c87-49f2-b397-fb2131041b15");
        MvcResult result = mockMvc.perform(
                get(basePath + "/users/{userId}/emails/{emailId}", defaultUserId, defaultEmailIdOne))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andReturn();

        Email actual = response.parse(result.getResponse().getContentAsString()).getObject();

        assertAll(
            () -> assertThat(actual.getId()).isEqualTo(defaultEmailIdOne)
        );
    }

    @Test
    void testGetInvalidEmailForUserIsNotFound() throws Exception {
        UUID userId = UUID.fromString("e711f133-ab0c-483e-8507-81416745c78e");
        mockMvc.perform(
                get(basePath + "/users/{userId}/emails/{emailId}", userId, randomUUID()))
            .andExpect(status().isNotFound())
            .andReturn();
    }

    @Test
    void testSendValidEmailIsCreated() throws Exception {
        Email email = new Email();
        email.setSender("test_sender_one@test.com");
        email.setRecipients(List.of("test_recipient@test.com"));
        email.setSubject("test email subject one");
        email.setBody("test email body one");

        MvcResult result = mockMvc.perform(post(basePath + "/users/{userId}/send-email", defaultUserId)
                .contentType(APPLICATION_JSON)
                .content(response.write(email).getJson()))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andReturn();

        Email actual = response.parse(result.getResponse().getContentAsString()).getObject();

        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getSender()).isEqualTo(email.getSender()),
            () -> assertThat(actual.getRecipients()).isEqualTo(email.getRecipients()),
            () -> assertThat(actual.getSubject()).isEqualTo(email.getSubject()),
            () -> assertThat(actual.getBody()).isEqualTo(email.getBody()),
            () -> assertThat(actual.getCreatedOn()).isNotNull(),
            () -> assertThat(actual.getModifiedOn()).isNotNull());
    }

    @Test
    void testSendInvalidEmailIsBadRequest() throws Exception {
        Email email = new Email();
        email.setRecipients(List.of("test_recipient@test.com"));
        email.setSubject("test email subject one");
        email.setBody("test email body one");

        UUID userId = randomUUID();
        mockMvc.perform(post(basePath + "/users/{userId}/send-email", userId)
                .contentType(APPLICATION_JSON)
                .content(response.write(email).getJson()))
            .andExpect(status().isBadRequest())
            .andReturn();

    }
}
