package org.elementcraft.dailyQuests.cmd;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.elementcraft.dailyQuests.manager.QuestManager;

@Command(name = "ElementTestPlugin")
public class ElementTestPluginCommand {

    private final QuestManager questManager;

    public ElementTestPluginCommand(QuestManager questManager) {
        this.questManager = questManager;
    }

    @Execute(name = "reset")
    void command(@Context CommandSender commandSender, @Arg Player player) {
        questManager.resetQuestsProgress(player);

        commandSender.sendMessage("Прогроесс игорока сброшен");
    }



}
