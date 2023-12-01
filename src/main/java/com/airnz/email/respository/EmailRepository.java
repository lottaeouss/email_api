package com.airnz.email.respository;

import static java.time.Instant.now;

import com.airnz.email.model.Email;
import com.airnz.email.rest.exception.ResourceNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class EmailRepository {

    private final Map<String, Email> emailIdMap = loadHardCodedData();

    private Map<String, Email> loadHardCodedData() {
        Map<String, Email> emailIdMap = new ConcurrentHashMap<>();
        UUID defaultUserId = UUID.fromString("e711f133-ab0c-483e-8507-81416745c78e");
        UUID defaultEmailIdOne = UUID.fromString("3302f3b6-3c87-49f2-b397-fb2131041b15");
        UUID defaultEmailIdTwo = UUID.fromString("b2a2c0be-2ff8-484f-951e-53e6c81dcf69");

        List<UUID> defaultEmails = List.of(defaultEmailIdOne, defaultEmailIdTwo);

        for (UUID emailId : defaultEmails) {
            emailIdMap.put(buildKey(defaultUserId, emailId), buildDefaultEmail(emailId));
        }

        return emailIdMap;
    }

    private String buildKey(UUID userId, UUID emailId) {
        return userId.toString() + "#" + emailId.toString();
    }

    public Email getEmailByIdAndUserId(UUID userId, UUID emailId) {
        String key = buildKey(userId, emailId);
        if (emailIdMap.containsKey(key)) {
            return emailIdMap.get(key);
        }
        throw new ResourceNotFoundException();
    }

    private Email buildDefaultEmail(UUID id) {
        Email defaultEmailOne = new Email();
        defaultEmailOne.setId(id);
        defaultEmailOne.setSender("test_sender_one@test.com");
        defaultEmailOne.setRecipients(List.of("test_recipient@test.com"));
        defaultEmailOne.setSubject("test email subject");
        defaultEmailOne.setBody("test email body");
        defaultEmailOne.setCreatedOn(now());
        defaultEmailOne.setModifiedOn(now());
        return defaultEmailOne;
    }

}
