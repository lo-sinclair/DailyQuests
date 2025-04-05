package org.elementcraft.dailyQuests.entity.quests;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.elementcraft.dailyQuests.entity.IQuestListener;
import org.elementcraft.dailyQuests.entity.Quest;
import org.elementcraft.dailyQuests.entity.QuestType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class KillMobQuest extends Quest implements IQuestListener {
    private final EntityType targetMobType;

    public KillMobQuest(String id, String description, int targetAmount, int reward, Material icon, EntityType targetMobType) {
        super(id, description, QuestType.KILL_MOBS, targetAmount, reward, icon);
        this.targetMobType = targetMobType;
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
