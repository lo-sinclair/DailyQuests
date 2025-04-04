package org.elementcraft.dailyQuests.quest.quests;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.elementcraft.dailyQuests.quest.Quest;
import org.elementcraft.dailyQuests.quest.QuestType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BreakBlockQuest extends Quest implements Listener {
    private final Material targetBlockType; // null — любой блок
    private final Map<UUID, Integer> progress = new HashMap<>();

    public BreakBlockQuest(String id, String description, int targetAmount, int reward, Material targetBlockType) {
        super(id, description, QuestType.BREAK_BLOCKS, targetAmount, reward);
        this.targetBlockType = targetBlockType;
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
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (targetBlockType == null || block.getType() == targetBlockType) {
            UUID playerId = player.getUniqueId();
            progress(playerId);
            if (isComplete(playerId)) {
                player.sendMessage("Квест выполнен: " + getDescription());
            }
        }
    }
}
