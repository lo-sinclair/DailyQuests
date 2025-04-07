package org.elementcraft.dailyQuests.quest.quests;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.elementcraft.dailyQuests.quest.Quest;
import org.elementcraft.dailyQuests.quest.QuestType;
import org.elementcraft.dailyQuests.quest.event.PlayerCompletedQuestEvent;

import java.util.UUID;

public class CompleteThreeQuestsQuest extends Quest implements Listener {

    public CompleteThreeQuestsQuest(String id, String description, int reward, Material icon) {
        super(id, description, QuestType.SPECIAL, 3, reward, icon, false);
    }

    @Override
    public void register(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerCompletedQuest(PlayerCompletedQuestEvent event) {
        UUID playerId = event.getPlayer().getUniqueId();

        if (event.getQuest().getId().equals(this.id)) return;

        int currentProgress = getProgress(playerId);

        if (!isComplete(playerId) && currentProgress < 3) {
            progress(playerId);

            if (currentProgress + 1 == 3) {
                event.getPlayer().sendMessage("Ты выполнил 3 квеста за день! Бонусный квест завершён.");
            }
        }
    }
}
