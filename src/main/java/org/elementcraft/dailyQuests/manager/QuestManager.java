package org.elementcraft.dailyQuests.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.elementcraft.dailyQuests.IQuest;
import org.elementcraft.dailyQuests.quest.Quest;
import org.elementcraft.dailyQuests.quest.model.PlayerQuestData;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class QuestManager {
    private static QuestManager instance;
    private final JavaPlugin plugin;
    //private final QuestProgressRepository progressRepository;

    private final Map<UUID, List<PlayerQuestData>> playerQuests = new HashMap<>();
    private final Map<String, IQuest> availableQuests = new HashMap<>();
    private final Map<UUID, Map<LocalDate, Integer>> dailyCompletions = new HashMap<>();

    /*public QuestManager(JavaPlugin plugin, QuestProgressRepository progressRepository) {
        this.plugin = plugin;
        this.progressRepository = progressRepository;
    }*/

    private QuestManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /*public void loadAllProgress() {
        List<QuestProgressEntity> entities = progressRepository.loadAll();

        for (QuestProgressEntity entity : entities) {
            UUID playerId = entity.getPlayerId();
            IQuest quest = getQuestById(entity.getQuestId());

            if (quest != null) {
                // Восстановить прогресс
                quest.restoreProgress(playerId, entity.getProgress());

                // Получаем список квестов игрока
                List<PlayerQuestData> quests = playerQuests.get(playerId);
                if (quests == null) {
                    quests = new ArrayList<>();
                    playerQuests.put(playerId, quests);
                }

                // Проверяем, нет ли уже такого квеста
                boolean alreadyAdded = false;
                for (PlayerQuestData data : quests) {
                    if (data.getQuest().equals(quest)) {
                        alreadyAdded = true;
                        break;
                    }
                }

                if (!alreadyAdded) {
                    quests.add(new PlayerQuestData(quest, entity.getAssignedDate()));
                }
            }
        }
    }*/

    public void resetQuestsProgress(Player player) {
        for( PlayerQuestData data  : playerQuests.get(player.getUniqueId()) ) {
            data.getQuest().resetProgress(player.getUniqueId());
        }
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

        List<IQuest> assignableQuests = availableQuests.values().stream()
                .filter(q -> q instanceof Quest quest && quest.isAssignable())
                .collect(Collectors.toList());

        // Нет квестов или старые
        if (quests == null || quests.isEmpty() || quests.stream().anyMatch(q -> !q.getAssignedDate().equals(today))) {
            Collections.shuffle(assignableQuests);

            List<PlayerQuestData> newQuests = assignableQuests.stream()
                    .limit(2)
                    .map(q -> new PlayerQuestData(q, today))
                    .collect(Collectors.toList());

            playerQuests.put(playerId, newQuests);
            return newQuests.stream().map(PlayerQuestData::getQuest).toList();
        }

        // Один квест
        if (quests.size() == 1) {
            IQuest existingQuest = quests.get(0).getQuest();

            List<IQuest> filtered = new ArrayList<>(assignableQuests);
            filtered.remove(existingQuest); // чтобы не дублировался
            Collections.shuffle(filtered);

            if (!filtered.isEmpty()) {
                PlayerQuestData newQuest = new PlayerQuestData(filtered.get(0), today);
                quests.add(newQuest);
            }
        }

        return quests.stream().map(PlayerQuestData::getQuest).toList();
    }

    public Optional<IQuest> getQuestOptionalById(String id) {
        return Optional.ofNullable(availableQuests.get(id));
    }

    public int incrementDailyCompletions(UUID playerId) {
        LocalDate today = LocalDate.now();
        Map<LocalDate, Integer> dateMap = dailyCompletions.get(playerId);
        if (dateMap == null) {
            dateMap = new HashMap<>();
            dailyCompletions.put(playerId, dateMap);
        }

        int newCount = dateMap.getOrDefault(today, 0) + 1;
        dateMap.put(today, newCount);
        return newCount;
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
        if (!hasQuest(player.getUniqueId(), quest)) {
            player.sendMessage("Ты уже получил награду.");
            return;
        }
        EconomyManager.giveMoney(player, quest.getReward());
        player.sendMessage("Ты получил награду " + quest.getReward() + " золотых");
        removeQuestFromPlayer(player.getUniqueId(), quest);
    }

    public static void init(JavaPlugin plugin) {
        if (instance == null) {
            instance = new QuestManager(plugin);
        }
    }

    public static QuestManager getInstance() {
        return instance;
    }
}
