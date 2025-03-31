package org.elementcraft.dailyQuests;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class DailyQuests extends JavaPlugin {
    private static DailyQuests instance;

    @Override
    public void onEnable() {
        ConsoleCommandSender console = getServer().getConsoleSender();
        console.sendMessage("\n");
        console.sendMessage(ChatColor.GREEN + "|~~~~~~~~~~~~|");
        console.sendMessage(ChatColor.GREEN + "| DailyQuests|");
        console.sendMessage(ChatColor.GREEN + "|~~~~~~~~~~~~|");
        console.sendMessage(ChatColor.GRAY + "test task for " + ChatColor.GOLD + "ElementCraft" + ChatColor.GRAY + " server");
        console.sendMessage(ChatColor.GRAY + "[codded by locb_km with love \u2764]"); //with love \u2764"

        console.sendMessage("\n");

        instance = this;

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static DailyQuests getInstance() {
        return instance;
    }
}
