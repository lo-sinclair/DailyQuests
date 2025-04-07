package org.elementcraft.dailyQuests.quest;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.elementcraft.dailyQuests.manager.QuestManager;
import org.elementcraft.dailyQuests.quest.event.PlayerCompletedQuestEvent;

import java.util.*;

public abstract class Quest implements IQuest {
    protected final String id;
    protected final String description;
    protected final QuestType type;
    protected final int targetAmount;
    protected final int reward;
    private final Material icon;
    private final boolean assignable;

    private final Map<UUID, Integer> progress = new HashMap<>();

    public Quest(String id, String description, QuestType type, int targetAmount, int reward, Material icon, boolean assignable) {
        this.id = id;
        this.description = description;
        this.type = type;
        this.targetAmount = targetAmount;
        this.reward = reward;
        this.icon = icon;
        this.assignable = assignable;
    }

    @Override
    public boolean isComplete(UUID playerId) {
        return progress.getOrDefault(playerId, 0) >= targetAmount;
    }

    @Override
    public void progress(UUID playerId) {
        int newProgress = progress.getOrDefault(playerId, 0) + 1;
        progress.put(playerId, newProgress);

        if (newProgress == targetAmount) {
            Player player = Bukkit.getPlayer(playerId);
            if (player != null) {
                int dailyCount = QuestManager.getInstance().incrementDailyCompletions(playerId);
                Bukkit.getPluginManager().callEvent(new PlayerCompletedQuestEvent(player, this, dailyCount));
            }
        }
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
    @Override public boolean isAssignable() {
        return assignable;
    }

    public abstract void register(JavaPlugin plugin);

    @Override
    public List<String> activeQuestText(Player player) {
        ArrayList<String> text = new ArrayList<>();
        text.add("&f" + getDescription());
        text.add("");
        text.add("&eПрогресс: " + getProgress(player.getUniqueId()) + " из " + getTargetAmount());
        text.add("&fНаграда: " + getReward() + " золотых");
        return text;
    }

    @Override
    public List<String> completedQuestText(Player player) {
        ArrayList<String> text = new ArrayList<>();
        text.add("&f" + getDescription());
        text.add("");
        text.add("&aПрогресс: " + getProgress(player.getUniqueId()) + " из " + getTargetAmount());
        text.add("&fНаграда: " + getReward() + " золотых");
        text.add("&7Нажмите ЛКМ, чтобы забрать нагрду");
        return text;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Quest other = (Quest) obj;
        return id.equals(other.id);
    }

    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Quest{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", targetAmount=" + targetAmount +
                ", reward=" + reward +
                ", icon=" + icon +
                ", progress=" + progress +
                '}';
    }

}
