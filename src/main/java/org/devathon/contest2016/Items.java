package org.devathon.contest2016;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by Redraskal_2 on 11/5/2016.
 */
public class Items {

    public static ItemStack craftSolarPanel() {
        ItemStack itemStack = new ItemStack(Material.OBSIDIAN, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&eSolar Panel"));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}