package com.airnz.email.respository;

import com.airnz.email.model.DraftEmail;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class DraftEmailRepository {

    private final Map<UUID, Set<UUID>> userIdDraftEmailIdsMap = new ConcurrentHashMap<>();
    private final Map<UUID, DraftEmail> draftEmailIdMap = new ConcurrentHashMap<>();

    public DraftEmail saveDraftEmail(UUID userId, DraftEmail draftEmail) {
        userIdDraftEmailIdsMap.putIfAbsent(userId, new HashSet<>());
        userIdDraftEmailIdsMap.get(userId).add(draftEmail.getId());
        draftEmailIdMap.put(draftEmail.getId(), draftEmail);
        return draftEmail;
    }
}
