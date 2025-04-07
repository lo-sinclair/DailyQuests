package org.elementcraft.dailyQuests.quest.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.elementcraft.dailyQuests.manager.QuestManager;
import org.elementcraft.dailyQuests.quest.QuestType;
import org.elementcraft.dailyQuests.quest.quests.BreakBlockQuest;
import org.elementcraft.dailyQuests.quest.quests.KillMobQuest;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class QuestConfigLoader {
    private final QuestManager questManager;
    private final Path configPath;

    public QuestConfigLoader(QuestManager questManager, File dataFolder) {
        this.questManager = questManager;
        this.configPath = dataFolder.toPath().resolve("quests.conf");
    }

    public void load() {
        try {
            if (Files.notExists(configPath)) {
                Files.createDirectories(configPath.getParent());
                Files.copy(Objects.requireNonNull(getClass().getResourceAsStream("/quests.conf")), configPath);
            }

            ConfigurationNode root = HoconConfigurationLoader.builder()
                    .path(configPath)
                    .build()
                    .load();

            List<? extends ConfigurationNode> questNodes = root.node("quests").childrenList();

            for (ConfigurationNode node : questNodes) {
                try {
                    String id = node.node("id").getString();
                    String description = node.node("description").getString();
                    int amount = node.node("amount").getInt();
                    int reward = node.node("reward").getInt();
                    Material icon = Material.valueOf(node.node("icon").getString());
                    QuestType type = QuestType.valueOf(node.node("type").getString());
                    String targetRaw = node.node("target").getString();

                    switch (type) {
                        case KILL_MOBS -> {
                            EntityType entity = EntityType.valueOf(targetRaw);
                            questManager.registerQuest(new KillMobQuest(id, description, amount, reward, icon, entity));
                        }
                        case BREAK_BLOCKS -> {
                            Material block = Material.valueOf(targetRaw);
                            questManager.registerQuest(new BreakBlockQuest(id, description, amount, reward, icon, block));
                        }
                        default -> Bukkit.getLogger().warning("Unknown quest type: " + type);
                    }

                } catch (Exception e) {
                    Bukkit.getLogger().warning("Error loading one of the quests: " + e.getMessage());
                }
            }

        } catch (IOException e) {
            Bukkit.getLogger().severe("Failed to load quest configuration file: " + e.getMessage());
        }
    }
}
