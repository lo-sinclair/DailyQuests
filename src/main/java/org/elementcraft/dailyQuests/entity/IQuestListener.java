package org.elementcraft.dailyQuests.entity;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public interface IQuestListener extends Listener {
    void register(JavaPlugin plugin);
}
