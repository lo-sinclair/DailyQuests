package org.elementcraft.dailyQuests.quest.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.elementcraft.dailyQuests.quest.IQuest;
import org.jetbrains.annotations.NotNull;

public class PlayerCompletedQuestEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private final Player player;
    private final IQuest quest;
    private final int questsCompletedToday;

    public PlayerCompletedQuestEvent(Player player, IQuest quest, int questsCompletedToday) {
        this.player = player;
        this.quest = quest;
        this.questsCompletedToday = questsCompletedToday;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public Player getPlayer() {
        return player;
    }

    public IQuest getQuest() {
        return quest;
    }

    public int getQuestsCompletedToday() {
        return questsCompletedToday;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
