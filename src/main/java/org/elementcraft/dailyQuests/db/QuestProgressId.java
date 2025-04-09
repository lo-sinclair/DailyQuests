package org.elementcraft.dailyQuests.db;

import java.io.Serializable;
import java.util.Objects;

public class QuestProgressId implements Serializable {
    private String playerId;
    private String questId;

    public QuestProgressId() {}

    public QuestProgressId(String playerId, String questId) {
        this.playerId = playerId;
        this.questId = questId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuestProgressId that)) return false;
        return Objects.equals(playerId, that.playerId) && Objects.equals(questId, that.questId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerId, questId);
    }
}
