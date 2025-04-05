package org.elementcraft.dailyQuests.entity;

import org.bukkit.Material;

import java.util.UUID;

public interface IQuest {
    String getId();
    String getDescription();
    QuestType getType();
    int getTargetAmount();
    int getReward();
    Material getIcon();

    boolean isComplete(UUID playerId);
    void progress(UUID playerId);
    int getProgress(UUID playerId);
    void resetProgress(UUID playerId);
}
