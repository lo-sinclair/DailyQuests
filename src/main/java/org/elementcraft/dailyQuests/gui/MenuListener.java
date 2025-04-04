package org.elementcraft.dailyQuests.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

import java.util.Optional;

public class MenuListener implements Listener {

    @EventHandler
    public void oneMenuClick(InventoryClickEvent e) {
        if(e.getCurrentItem() == null) {
            return;
        }
        Player player = (Player) e.getWhoClicked();

        InventoryHolder holder = e.getClickedInventory().getHolder();

        if (holder instanceof Menu) {
            e.setCancelled(true);
            Menu menu = (Menu) holder;
            menu.getButtonBySlot(e.getSlot()).ifPresent(button -> button.onClick(player));
        }

    }

}
