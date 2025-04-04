package org.elementcraft.dailyQuests.quest;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class QuestManager {


    private static final Map<UUID, List<IQuest>> playerQuests = new HashMap<>();
    private final Map<String, IQuest> availableQuests = new HashMap<>();

    private final JavaPlugin plugin;

    public QuestManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void registerQuest(IQuest quest) {
        availableQuests.put(quest.getId(), quest);

        if (quest instanceof Listener listener) {
            Bukkit.getPluginManager().registerEvents(listener, plugin);
        }
    }

    public IQuest getQuestById(String id) {
        return availableQuests.get(id);
    }

    public Collection<IQuest> getAllQuests() {
        return availableQuests.values();
    }

    public void addQuestToPlayer(UUID playerId, IQuest quest) {
        List<IQuest> quests = playerQuests.get(playerId);
        if (quests == null) {
            quests = new ArrayList<>();
            playerQuests.put(playerId, quests);
        }

        quests.add(quest);
    }

    public List<IQuest> getPlayerQuests(UUID playerId) {
        List<IQuest> quests = playerQuests.get(playerId);
        if (quests == null) {
            return Collections.emptyList();
        }
        return quests;
    }

    public void removeQuestFromPlayer(UUID playerId, IQuest quest) {
        List<IQuest> quests = playerQuests.get(playerId);
        if (quests != null) {
            quests.remove(quest);
        }
    }

    public void clearQuestsForPlayer(UUID playerId) {
        playerQuests.remove(playerId);
    }

    public boolean hasQuest(UUID playerId, IQuest quest) {
        List<IQuest> quests = playerQuests.get(playerId);
        return quests != null && quests.contains(quest);
    }

}
