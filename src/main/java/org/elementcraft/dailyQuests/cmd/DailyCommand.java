package org.elementcraft.dailyQuests.cmd;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.elementcraft.dailyQuests.DailyQuests;
import org.elementcraft.dailyQuests.entity.IQuest;
import org.elementcraft.dailyQuests.entity.Quest;
import org.elementcraft.dailyQuests.gui.Button;
import org.elementcraft.dailyQuests.gui.Menu;
import org.elementcraft.dailyQuests.manager.QuestManager;

import java.util.ArrayList;
import java.util.List;


@Command(name = "daily")
@Permission("daily.use")
public class DailyCommand {

    private final QuestManager questManager;

    public DailyCommand() {
        this.questManager = DailyQuests.getInstance().getQuestManager();
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
                        activeQuestText(quest, player),  p -> {}));
            }
            else {
                menu.addButton(new Button(quest.getIcon(), slots[i], "&aЕжедневное задание",
                        completedQuestText(quest, player),  p -> {
                    questManager.takeReward(p, quest);
                    player.closeInventory();
                }));
            }
        }

        menu.addButton(new Button(Material.GRASS_BLOCK, 24, "&eБонусное> задание", p -> {}));
        menu.show(player);
    }

    private List<String> activeQuestText(Quest quest, Player player) {
        ArrayList<String> text = new ArrayList<>();
        text.add("&f" + quest.getDescription());
        text.add("");
        text.add("&eПрогресс: " + quest.getProgress(player.getUniqueId()) + " из" + quest.getTargetAmount());
        text.add("&fНаграда: " + quest.getReward() + " золотых");
        return text;
    }

    private List<String> completedQuestText(Quest quest, Player player) {
        ArrayList<String> text = new ArrayList<>();
        text.add("&f" + quest.getDescription());
        text.add("");
        text.add("&aПрогресс: " + quest.getProgress(player.getUniqueId()) + " из" + quest.getTargetAmount());
        text.add("&fНаграда: " + quest.getReward() + " золотых");
        text.add("&7Нажмите ЛКМ, чтобы забрать нагрду");
        return text;
    }

}
