package org.devathon.contest2016.listener;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.devathon.contest2016.SolarPanel;
import org.devathon.contest2016.Utils;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by Redraskal_2 on 11/5/2016.
 */
public class BlockPlace implements Listener {

    private JavaPlugin javaPlugin;

    public BlockPlace(JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent blockPlaceEvent) {
        if(blockPlaceEvent.getBlockPlaced().getType() == Material.OBSIDIAN) {
            if(blockPlaceEvent.getItemInHand().hasItemMeta()
                    && blockPlaceEvent.getItemInHand().getItemMeta().getDisplayName() != null) {
                String displayName = blockPlaceEvent.getItemInHand().getItemMeta().getDisplayName();
                if(displayName.equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&eSolar Panel"))) {
                    SolarPanel solarPanel =
                            new SolarPanel(blockPlaceEvent.getBlockPlaced(), UUID.randomUUID(), this.javaPlugin);
                    new BukkitRunnable() {
                        public void run() {
                            try {
                                Utils.save(solarPanel);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.runTaskAsynchronously(this.javaPlugin);
                }
            }
        }
    }
}