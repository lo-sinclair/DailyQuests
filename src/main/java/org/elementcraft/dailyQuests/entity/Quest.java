package org.elementcraft.dailyQuests.entity;

import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class Quest implements IQuest{
    protected final String id;
    protected final String description;
    protected final QuestType type;
    protected final int targetAmount;
    protected final int reward;
    private final Material icon;

    private final Map<UUID, Integer> progress = new HashMap<>();

    public Quest(String id, String description, QuestType type, int targetAmount, int reward, Material icon) {
        this.id = id;
        this.description = description;
        this.type = type;
        this.targetAmount = targetAmount;
        this.reward = reward;
        this.icon = icon;
    }

    @Override
    public boolean isComplete(UUID playerId) {
        return progress.getOrDefault(playerId, 0) >= targetAmount;
    }

    @Override
    public void progress(UUID playerId) {
        progress.put(playerId, progress.getOrDefault(playerId, 0) + 1);
    }

    @Override
    public int getProgress(UUID playerId) {
        return progress.getOrDefault(playerId, 0);
    }

    @Override
    public void resetProgress(UUID playerId) {
        progress.remove(playerId);
    }

    @Override
    public void restoreProgress(UUID playerId, int value) {
        progress.put(playerId, value);
    }

    @Override public String getId() { return id; }
    @Override public String getDescription() { return description; }
    @Override public QuestType getType() { return type; }
    @Override public int getTargetAmount() { return targetAmount; }
    @Override public int getReward() { return reward; }
    @Override public Material getIcon() {
        return icon;
    }

    public abstract void register(JavaPlugin plugin);

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Quest other = (Quest) obj;
        return id.equals(other.id);
    }

    public int hashCode() {
        return id.hashCode();
    }
}
