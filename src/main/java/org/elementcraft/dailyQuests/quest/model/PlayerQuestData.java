package org.elementcraft.dailyQuests.quest.model;

import org.elementcraft.dailyQuests.quest.IQuest;

import java.time.LocalDate;

public class PlayerQuestData {
    private final IQuest quest;
    private final LocalDate assignedDate;

    public PlayerQuestData(IQuest quest, LocalDate assignedDate) {
        this.quest = quest;
        this.assignedDate = assignedDate;
    }

    public IQuest getQuest() {
        return quest;
    }

    public LocalDate getAssignedDate() {
        return assignedDate;
    }
}
