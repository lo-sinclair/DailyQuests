package org.elementcraft.dailyQuests.quest.quests;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.elementcraft.dailyQuests.quest.IQuestListener;
import org.elementcraft.dailyQuests.quest.Quest;
import org.elementcraft.dailyQuests.quest.QuestType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class KillMobQuest extends Quest implements IQuestListener {
    private final EntityType targetMobType;
    private final Map<UUID, Integer> progress = new HashMap<>();

    public KillMobQuest(String id, String description, int targetAmount, int reward, EntityType targetMobType) {
        super(id, description, QuestType.KILL_MOBS, targetAmount, reward);
        this.targetMobType = targetMobType;
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
    public void register(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onMobKill(EntityDeathEvent event) {
        Player player = event.getEntity().getKiller();
        if (player == null) return;

        if (targetMobType == null || event.getEntityType() == targetMobType) {
            progress(player.getUniqueId());
            if (isComplete(player.getUniqueId())) {
                player.sendMessage("Квест выполнен: " + getDescription());
            }
        }
    }
}
