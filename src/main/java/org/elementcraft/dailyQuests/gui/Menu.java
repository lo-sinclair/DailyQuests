package org.elementcraft.dailyQuests.gui;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Menu implements InventoryHolder{
    private String title;

    private int size;

    private List<Button> buttons = new ArrayList<>();

    private Inventory inventory;

    public Menu(String title, int size) {
        this.title = title;
        this.size = size;

        inventory = Bukkit.createInventory(this, size, ChatColor.translateAlternateColorCodes('&', title));
    }

    public Menu addButton(Button button) {
        buttons.add(button);

        inventory.setItem(button.getSlot(), button.getItemStack());

        return this;
    }

    public Optional<Button> getButtonBySlot(int slot) {
        return buttons.stream()
                .filter(b -> b.getSlot() == slot)
                .findFirst();
    }

    public void show(Player player) {
        player.openInventory(inventory);
    }


    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
