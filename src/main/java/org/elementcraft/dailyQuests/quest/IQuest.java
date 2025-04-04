package org.elementcraft.dailyQuests.quest;

import java.util.UUID;

public interface IQuest {
    String getId();
    String getDescription();
    QuestType getType();
    int getTargetAmount();
    int getReward();

    boolean isComplete(UUID playerId);
    void progress(UUID playerId);
}
