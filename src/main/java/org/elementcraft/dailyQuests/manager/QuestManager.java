package org.elementcraft.dailyQuests.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.elementcraft.dailyQuests.entity.IQuest;
import org.elementcraft.dailyQuests.entity.model.PlayerQuestData;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class QuestManager {


    private final Map<UUID, List<PlayerQuestData>> playerQuests = new HashMap<>();
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



    public List<IQuest> getOrAssignDailyQuests(UUID playerId) {
        List<PlayerQuestData> quests = playerQuests.get(playerId);
        LocalDate today = LocalDate.now();

        // Нет квестов или старые
        if (quests == null || quests.isEmpty() || quests.stream().anyMatch(q -> !q.getAssignedDate().equals(today))) {
            List<IQuest> available = new ArrayList<>(availableQuests.values());
            Collections.shuffle(available);

            List<PlayerQuestData> newQuests = available.stream()
                    .limit(2)
                    .map(q -> new PlayerQuestData(q, today))
                    .collect(Collectors.toList());

            playerQuests.put(playerId, newQuests);
            return newQuests.stream().map(PlayerQuestData::getQuest).toList();
        }

        // Один квест
        if (quests.size() == 1) {
            IQuest existingQuest = quests.get(0).getQuest();

            List<IQuest> available = new ArrayList<>(availableQuests.values());
            available.remove(existingQuest); // чтобы не дублировался
            Collections.shuffle(available);

            if (!available.isEmpty()) {
                PlayerQuestData newQuest = new PlayerQuestData(available.get(0), today);
                quests.add(newQuest);
            }
        }

        return quests.stream().map(PlayerQuestData::getQuest).toList();
    }



    public void removeQuestFromPlayer(UUID playerId, IQuest quest) {
        List<PlayerQuestData> quests = playerQuests.get(playerId);
        if (quests != null) {
            quests.removeIf(data -> data.getQuest().equals(quest));
            quest.resetProgress(playerId);
        }
    }

    public void clearQuestsForPlayer(UUID playerId) {
        playerQuests.remove(playerId);
    }

    public boolean hasQuest(UUID playerId, IQuest quest) {
        List<PlayerQuestData> quests = playerQuests.get(playerId);
        return quests != null && quests.stream().anyMatch(data -> data.getQuest().equals(quest));
    }

    public void takeReward(Player player, IQuest quest) {
        player.sendMessage("ТЫ ЗАБРАЛ НАГРАДУ");
        removeQuestFromPlayer(player.getUniqueId(), quest);
    }
}
