package org.elementcraft.dailyQuests.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.Consumer;

public class Button {

    private final int slot;
    private final ItemStack itemStack;
    private final Consumer<Player> onClick;


    public Button(Material material, int slot, Consumer<Player> onClick) {
        this.itemStack = new ItemStack(material);
        this.slot = slot;
        this.onClick = onClick;
    }

    public void onClick(Player player) {
        System.out.println(player);
        if (onClick == null) {
            throw new IllegalStateException("onClick не задан для кнопки в слоте " + slot);
        }
        onClick.accept(player);
    }

    /*public void onClick(Player player) {
        System.out.println("onClick вызван для слота: " + slot);
        if (onClick != null) {
            System.out.println("Вызываю onClick.accept() для игрока: " + player.getName());
            onClick.accept(player);
        } else {
            System.out.println("onClick == null для слота: " + slot);
        }
    }*/

    public int getSlot() {
        return slot;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
