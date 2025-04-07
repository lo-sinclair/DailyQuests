package org.elementcraft.dailyQuests;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.elementcraft.dailyQuests.quest.QuestType;

import java.util.List;
import java.util.UUID;

public interface IQuest {
    String getId();
    String getDescription();
    QuestType getType();
    int getTargetAmount();
    int getReward();
    Material getIcon();
    boolean isAssignable();

    boolean isComplete(UUID playerId);
    void progress(UUID playerId);
    int getProgress(UUID playerId);
    void resetProgress(UUID playerId);
    void restoreProgress(UUID playerId, int value);

    List<String> activeQuestText(Player player);
    List<String> completedQuestText(Player player);
}
