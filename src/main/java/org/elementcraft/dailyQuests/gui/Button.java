package org.elementcraft.dailyQuests.gui;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.function.Consumer;

public class Button {

    private final int slot;
    private final ItemStack itemStack;
    private final Consumer<Player> onClick;
    private String title;


    public Button(Material material, int slot, String title, Consumer<Player> onClick) {
        this.itemStack = new ItemStack(material);
        this.slot = slot;
        this.onClick = onClick;
        setTitle(title);
    }

    public void onClick(Player player) {
        System.out.println(player);
        if (onClick == null) {
            throw new IllegalStateException("onClick not set for button in slot " + slot);
        }
        onClick.accept(player);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        System.out.println(title);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', title));
        itemStack.setItemMeta(itemMeta);
        System.out.println(itemStack.getItemMeta().getDisplayName());
    }

    public int getSlot() {
        return slot;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
