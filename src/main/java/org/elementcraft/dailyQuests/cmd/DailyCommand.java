package org.elementcraft.dailyQuests.cmd;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.elementcraft.dailyQuests.DailyQuests;
import org.elementcraft.dailyQuests.gui.Button;
import org.elementcraft.dailyQuests.gui.Menu;


@Command(name = "daily")
@Permission("daily.use")
public class DailyCommand {

    @Execute
    void command(@Context Player player) {

        Menu menu = new Menu("Quests", 45);
        menu.addButton(new Button(Material.ZOMBIE_HEAD, 20, p -> p.sendMessage("AAA")))
                .addButton(new Button(Material.BOW, 22, p -> p.sendMessage("BBB")))
                .addButton(new Button(Material.GRASS_BLOCK, 24, p -> p.sendMessage("CCC")));

        menu.show(player);

    }

}
