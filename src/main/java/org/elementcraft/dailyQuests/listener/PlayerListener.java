package org.elementcraft.dailyQuests.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.elementcraft.dailyQuests.manager.QuestManager;

public class PlayerListener implements Listener {
    private final QuestManager questManager;

    public PlayerListener(QuestManager questManager) {
        this.questManager = questManager;
    }

    @EventHandler
    public void onJoin(PlayerQuitEvent e) {
        questManager.saveProgress(e.getPlayer().getUniqueId());
    }
}
