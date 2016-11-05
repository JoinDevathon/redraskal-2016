package org.devathon.contest2016.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.devathon.contest2016.SolarPanel;

/**
 * Created by Redraskal_2 on 11/5/2016.
 */
public class BlockPlace implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent blockPlaceEvent) {
        if(blockPlaceEvent.getBlockPlaced().getType() == Material.OBSIDIAN) {
            new SolarPanel(blockPlaceEvent.getBlockPlaced());
        }
    }
}