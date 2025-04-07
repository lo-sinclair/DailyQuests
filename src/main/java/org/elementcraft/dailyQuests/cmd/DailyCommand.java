package org.elementcraft.dailyQuests.cmd;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;
import org.elementcraft.dailyQuests.quest.IQuest;
import org.elementcraft.dailyQuests.quest.Quest;
import org.elementcraft.dailyQuests.gui.Button;
import org.elementcraft.dailyQuests.gui.Menu;
import org.elementcraft.dailyQuests.manager.QuestManager;

import java.util.List;
import java.util.Optional;


@Command(name = "daily")
@Permission("daily.use")
public class DailyCommand {

    private final QuestManager questManager;

    public DailyCommand() {
        this.questManager = QuestManager.getInstance();
    }

    @Execute
    void command(@Context Player player) {

        List<IQuest> quests = questManager.getOrAssignDailyQuests(player.getUniqueId());

        Menu menu = new Menu("Quests", 45);

        int[] slots = {20, 22};
        for (int i = 0; i < slots.length; i++) {
            Quest quest = (Quest) quests.get(i);
            if(!quest.isComplete(player.getUniqueId())) {
                menu.addButton(new Button(quest.getIcon(), slots[i], "&eЕжедневное задание",
                        quest.activeQuestText(player), p -> {}));
            }
            else {
                menu.addButton(new Button(quest.getIcon(), slots[i], "&aЕжедневное задание",
                        quest.activeQuestText(player),  p -> {
                    questManager.takeReward(p, quest);
                    player.closeInventory();
                }));
            }
        }

        Optional<IQuest> bonusQuest = questManager.getQuestOptionalById("bonus_quest");
        bonusQuest.ifPresent(quest -> {
            if(!quest.isComplete(player.getUniqueId())) {
                menu.addButton(new Button(quest.getIcon(), 24, "&eБонусное задание",
                        quest.activeQuestText(player), p -> {}));
            }
            else {
                menu.addButton(new Button(quest.getIcon(), 24, "&aБонусное задание",
                        quest.completedQuestText(player), p -> {
                    questManager.takeReward(p, quest);
                    player.closeInventory();
                }));
            }
        });

        menu.show(player);
    }

}
