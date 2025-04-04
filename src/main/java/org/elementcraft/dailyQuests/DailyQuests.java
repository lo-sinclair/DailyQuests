package org.elementcraft.dailyQuests;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.bukkit.LiteBukkitMessages;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.elementcraft.dailyQuests.cmd.DailyCommand;
import org.elementcraft.dailyQuests.gui.MenuListener;

public final class DailyQuests extends JavaPlugin {
    private static DailyQuests instance;

    private LiteCommands<CommandSender> liteCommands;

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

        this.liteCommands = LiteBukkitFactory.builder("DailyQuests")
                .commands(new DailyCommand())
                .message(LiteBukkitMessages.PLAYER_ONLY, "&cOnly player can execute this command!")
                .build();

        getServer().getPluginManager().registerEvents(new MenuListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static DailyQuests getInstance() {
        return instance;
    }
}
