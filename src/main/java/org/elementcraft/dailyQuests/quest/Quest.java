package org.elementcraft.dailyQuests.quest;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class Quest implements IQuest{
    protected final String id;
    protected final String description;
    protected final QuestType type;
    protected final int targetAmount;
    protected final int reward;

    public Quest(String id, String description, QuestType type, int targetAmount, int reward) {
        this.id = id;
        this.description = description;
        this.type = type;
        this.targetAmount = targetAmount;
        this.reward = reward;
    }

    @Override public String getId() { return id; }
    @Override public String getDescription() { return description; }
    @Override public QuestType getType() { return type; }
    @Override public int getTargetAmount() { return targetAmount; }
    @Override public int getReward() { return reward; }

    public abstract void register(JavaPlugin plugin);
}
