package org.elementcraft.dailyQuests.quest;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public interface IQuestListener extends Listener {
    void register(JavaPlugin plugin);
}
