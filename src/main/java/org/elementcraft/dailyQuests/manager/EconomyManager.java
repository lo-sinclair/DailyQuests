package org.elementcraft.dailyQuests.manager;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class EconomyManager {
    private static Economy eco;

    public static void init() {
        RegisteredServiceProvider<Economy> reg =  Bukkit.getServicesManager().getRegistration(Economy.class);
        if(reg != null) eco = reg.getProvider();
    }

    public static void giveMoney(Player p, double amount) {
        if(eco == null) return;
        eco.depositPlayer(p, amount);
    }

}
