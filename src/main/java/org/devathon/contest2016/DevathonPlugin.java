package org.devathon.contest2016;

import org.bukkit.plugin.java.JavaPlugin;
import org.devathon.contest2016.listener.BlockPlace;

public class DevathonPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new BlockPlace(), this);
    }

    @Override
    public void onDisable() {
        // put your disable code here
    }
}