package com.airnz.email.respository;

import static java.time.Instant.now;
import static java.util.UUID.randomUUID;

import com.airnz.email.model.Email;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class EmailRepository {
    private final Map<UUID, List<Email>> userEmailMap = new ConcurrentHashMap<>();

    public List<Email> getEmailsByUserId(UUID userId) {
        if (!userEmailMap.containsKey(userId)) {
            return getDefaultEmailList();
        }
        return userEmailMap.get(userId);
    }

    private List<Email> getDefaultEmailList() {
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

        return List.of(defaultEmailOne, defaultEmailTwo);
    }

}
