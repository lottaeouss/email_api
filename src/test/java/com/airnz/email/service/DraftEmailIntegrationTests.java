package com.airnz.email.service;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.airnz.email.model.DraftEmail;
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
public class DraftEmailIntegrationTests {

    @Value("${resource.path}")
    private String basePath;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<DraftEmail> createResponse;

    @Test
    void testSendDraftEmailIsCreated() throws Exception {
        DraftEmail draftEmail = new DraftEmail();
        draftEmail.setSender("test_sender_one@test.com");
        draftEmail.setRecipients(List.of("test_recipient@test.com"));
        draftEmail.setSubject("test email subject one");
        draftEmail.setBody("test email body one");

        UUID userId = UUID.fromString("e711f133-ab0c-483e-8507-81416745c78e");
        MvcResult result = mockMvc.perform(post(basePath + "/users/{userId}/draft-email", userId)
                .contentType(APPLICATION_JSON)
                .content(createResponse.write(draftEmail).getJson()))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andReturn();

        DraftEmail actual = createResponse.parse(result.getResponse().getContentAsString()).getObject();

        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getSender()).isEqualTo(draftEmail.getSender()),
            () -> assertThat(actual.getRecipients()).isEqualTo(draftEmail.getRecipients()),
            () -> assertThat(actual.getSubject()).isEqualTo(draftEmail.getSubject()),
            () -> assertThat(actual.getBody()).isEqualTo(draftEmail.getBody()),
            () -> assertThat(actual.getCreatedOn()).isNotNull(),
            () -> assertThat(actual.getModifiedOn()).isNotNull());
    }

    @Test
    void testSendDraftEmailUserIsNotFound() throws Exception {
        DraftEmail draftEmail = new DraftEmail();
        draftEmail.setSender("test_sender_one@test.com");
        draftEmail.setRecipients(List.of("test_recipient@test.com"));
        draftEmail.setSubject("test email subject one");
        draftEmail.setBody("test email body one");

        mockMvc.perform(post(basePath + "/users/{userId}/draft-email", randomUUID())
                .contentType(APPLICATION_JSON)
                .content(createResponse.write(draftEmail).getJson()))
            .andExpect(status().isNotFound())
            .andReturn();
    }
}
