package org.elementcraft.dailyQuests;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.bukkit.LiteBukkitMessages;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.elementcraft.dailyQuests.cmd.DailyCommand;
import org.elementcraft.dailyQuests.cmd.ElementTestPluginCommand;
import org.elementcraft.dailyQuests.db.HibernateUtil;
import org.elementcraft.dailyQuests.db.QuestProgressRepository;
import org.elementcraft.dailyQuests.gui.MenuListener;
import org.elementcraft.dailyQuests.listener.PlayerListener;
import org.elementcraft.dailyQuests.manager.EconomyManager;
import org.elementcraft.dailyQuests.manager.QuestManager;
import org.elementcraft.dailyQuests.util.QuestConfigLoader;
import org.elementcraft.dailyQuests.quest.quests.CompleteThreeQuestsQuest;

import javax.persistence.EntityManager;

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
        console.sendMessage(ChatColor.GRAY + "[codded by locb_km]");

        console.sendMessage("\n");

        instance = this;


        EntityManager entityManager;
        try {
            entityManager = HibernateUtil.getEntityManager();
        } catch (Throwable t) {
            getLogger().severe("Hibernate initialization failed: " + t.getMessage());
            t.printStackTrace();
            return;
        }
        progressRepository = new QuestProgressRepository(entityManager);

        EconomyManager.init();

        QuestManager.init(this, progressRepository);
        QuestManager questManager = QuestManager.getInstance();

        new QuestConfigLoader(questManager, getDataFolder()).load();

        questManager.registerQuest(new CompleteThreeQuestsQuest(
                "bonus_quest", "Выполнить 3 ежедневных задания", 400, Material.GRASS_BLOCK
        ));



        questManager.loadAllProgress();

        this.liteCommands = LiteBukkitFactory.builder("DailyQuests")
                .commands(new DailyCommand(), new ElementTestPluginCommand(questManager))
                .message(LiteBukkitMessages.PLAYER_ONLY, "&cOnly player can execute this command!")
                .build();

        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(questManager), this);
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
        /*if (HibernateUtil.getEntityManagerFactory().isOpen()) {
            HibernateUtil.getEntityManagerFactory().close();
        }*/
        super.onDisable();
    }

    public QuestManager getQuestManager() {
        return questManager;
    }

    public static DailyQuests getInstance() {
        return instance;
    }
}
