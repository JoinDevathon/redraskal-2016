package org.devathon.contest2016;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.devathon.contest2016.listener.BlockPlace;

import java.io.IOException;

public class DevathonPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getLogger().info("[Startup] Pre-loading celestial angles...");
        Angle.calc(Bukkit.getWorlds().get(0));
        this.getLogger().info("[Startup] Spawning saved solar panels...");
        for(World world : Bukkit.getWorlds()) {
            try {
                Utils.load(world, this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.getLogger().info("[Startup] Registering listeners...");
        this.getServer().getPluginManager().registerEvents(new BlockPlace(this), this);
        this.updateRecipes();
    }

    private void updateRecipes() {
        this.getLogger().info("[Startup] Updating crafting recipes...");
        ShapedRecipe shapedRecipe = new ShapedRecipe(Items.craftSolarPanel());
        shapedRecipe.shape(" 1 ", " 2 ", " 3 ");
        shapedRecipe.setIngredient('1', Material.CARPET);
        shapedRecipe.setIngredient('2', Material.END_ROD);
        shapedRecipe.setIngredient('3', Material.OBSIDIAN);
        Bukkit.getServer().addRecipe(shapedRecipe);
    }

    @Override
    public void onDisable() {
        this.getLogger().info("[Startup] De-spawning solar panels...");
        SolarPanel.removeAll();
    }
}