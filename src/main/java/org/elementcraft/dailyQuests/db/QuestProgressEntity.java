package org.elementcraft.dailyQuests.db;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "quest_progress")
@IdClass(QuestProgressId.class)
public class QuestProgressEntity {

    @Id
    @Column(name = "player_id", nullable = false, length = 36, columnDefinition = "CHAR(36)")
    private String playerId;

    @Id
    @Column(name = "quest_id", nullable = false)
    private String questId;

    @Column(name = "progress")
    private int progress;

    @Column(name = "assigned_date")
    private LocalDate assignedDate;

    @Column(name = "completed")
    private boolean completed;

    public QuestProgressEntity() {}

    public QuestProgressEntity(String playerId, String questId, int progress, LocalDate assignedDate, boolean completed) {
        this.playerId = playerId;
        this.questId = questId;
        this.progress = progress;
        this.assignedDate = assignedDate;
        this.completed = completed;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getQuestId() {
        return questId;
    }

    public void setQuestId(String questId) {
        this.questId = questId;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public LocalDate getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(LocalDate assignedDate) {
        this.assignedDate = assignedDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}