package org.elementcraft.dailyQuests.db;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "quest_progress")
@IdClass(QuestProgressId.class)
public class QuestProgressEntity {

    @Id
    @Column(name = "player_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID playerId;

    @Id
    @Column(name = "quest_id", nullable = false, length = 255)
    private String questId;

    @Column(name = "progress")
    private int progress;

    @Column(name = "assigned_date")
    private LocalDate assignedDate;

    @Column(name = "completed")
    private boolean completed;

    public QuestProgressEntity() {
    }

    public QuestProgressEntity(UUID playerId, String questId, int progress, LocalDate assignedDate, boolean completed) {
        this.playerId = playerId;
        this.questId = questId;
        this.progress = progress;
        this.assignedDate = assignedDate;
        this.completed = completed;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
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