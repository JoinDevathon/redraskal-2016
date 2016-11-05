package org.devathon.contest2016;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.devathon.contest2016.listener.BlockPlace;

import java.io.IOException;

public class DevathonPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        for(World world : Bukkit.getWorlds()) {
            try {
                Utils.load(world, this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.getServer().getPluginManager().registerEvents(new BlockPlace(this), this);
    }

    @Override
    public void onDisable() {
        //TODO
    }
}