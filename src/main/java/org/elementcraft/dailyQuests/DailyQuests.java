package org.elementcraft.dailyQuests;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.bukkit.LiteBukkitMessages;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;
import org.elementcraft.dailyQuests.cmd.DailyCommand;
import org.elementcraft.dailyQuests.cmd.ElementTestPluginCommand;
import org.elementcraft.dailyQuests.db.HibernateUtil;
import org.elementcraft.dailyQuests.db.QuestProgressRepository;
import org.elementcraft.dailyQuests.gui.MenuListener;
import org.elementcraft.dailyQuests.manager.EconomyManager;
import org.elementcraft.dailyQuests.manager.QuestManager;
import org.elementcraft.dailyQuests.quest.QuestConfigLoader;
import org.elementcraft.dailyQuests.quest.quests.BreakBlockQuest;
import org.elementcraft.dailyQuests.quest.quests.KillMobQuest;

public final class DailyQuests extends JavaPlugin {
    private static DailyQuests instance;

    private LiteCommands<CommandSender> liteCommands;
    private QuestManager questManager;
    private QuestProgressRepository progressRepository;

    @Override
    public void onEnable() {
        ConsoleCommandSender console = getServer().getConsoleSender();
        console.sendMessage("\n");
        console.sendMessage(ChatColor.GREEN + "|~~~~~~~~~~~~|");
        console.sendMessage(ChatColor.GREEN + "| DailyQuests|");
        console.sendMessage(ChatColor.GREEN + "|~~~~~~~~~~~~|");
        console.sendMessage(ChatColor.GRAY + "test task for " + ChatColor.GOLD + "ElementCraft" + ChatColor.GRAY + " server");
        console.sendMessage(ChatColor.GRAY + "[codded by locb_km with love \u2764]"); //with love \u2764"

        console.sendMessage("\n");

        instance = this;


        /*EntityManager entityManager;
        try {
            entityManager = HibernateUtil.getEntityManager();
        } catch (Throwable t) {
            getLogger().severe("Hibernate initialization failed: " + t.getMessage());
            t.printStackTrace();
            return;
        }*/
        //progressRepository = new QuestProgressRepository(entityManager);

        //questManager = new QuestManager(this, progressRepository);
        EconomyManager.init();
        questManager = new QuestManager(this);
        new QuestConfigLoader(questManager, getDataFolder()).load();

        // Здесь загрузка из конфига
        /*questManager.registerQuest(new KillMobQuest(
                "kill_zombie", "Убей 5 зомби", 5, 200, Material.ZOMBIE_HEAD, EntityType.ZOMBIE
        ));
        questManager.registerQuest(new BreakBlockQuest(
                "break_stone", "Сломай 10 булыжников", 10, 200, Material.COBBLESTONE, Material.COBBLESTONE
        ));

        questManager.registerQuest(new BreakBlockQuest(
                "break_stone", "Сломай 3 диорита", 3, 200, Material.DIORITE, Material.DIORITE
        ));*/
        // ------------------

        //questManager.loadAllProgress();

        this.liteCommands = LiteBukkitFactory.builder("DailyQuests")
                .commands(new DailyCommand(), new ElementTestPluginCommand(questManager))
                .message(LiteBukkitMessages.PLAYER_ONLY, "&cOnly player can execute this command!")
                .build();

        getServer().getPluginManager().registerEvents(new MenuListener(), this);
    }


    @Override
    public void onDisable() {
        if (progressRepository != null) {
            progressRepository.close();
        }
        try {
            HibernateUtil.shutdown();
        } catch (Throwable t) {
            getLogger().warning("Could not shut down Hibernate cleanly: " + t.getMessage());
        }
        super.onDisable();
    }

    public QuestManager getQuestManager() {
        return questManager;
    }

    public static DailyQuests getInstance() {
        return instance;
    }
}
