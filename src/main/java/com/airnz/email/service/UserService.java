package com.airnz.email.service;

import com.airnz.email.rest.exception.ResourceNotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserService {

    private final List<UUID> usersList = buildHardcodedUserList();

    private List<UUID> buildHardcodedUserList() {
        List<UUID> defaultUsersList = List.of(
            UUID.fromString("e711f133-ab0c-483e-8507-81416745c78e")
        );
        return new CopyOnWriteArrayList<>(defaultUsersList);
    }

    private boolean userExists(UUID userId) {
        return usersList.contains(userId);
    }

    public void checkUserExists(UUID userId) {
        if (!userExists(userId)) {
            throw new ResourceNotFoundException();
        }
    }


}
