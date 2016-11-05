package org.devathon.contest2016;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Redraskal_2 on 11/5/2016.
 */
public class SolarPanel implements Listener {

    private JavaPlugin javaPlugin;
    private Location baseLocation;
    private Block baseBlock;
    private Block rod_one;
    private Block rod_two;

    private ArmorStand panel_left_one;
    private ArmorStand panel_left_two;
    private ArmorStand panel_left_three;

    private ArmorStand panel_middle_one;
    private ArmorStand panel_middle_two;
    private ArmorStand panel_middle_three;

    private ArmorStand panel_right_one;
    private ArmorStand panel_right_two;
    private ArmorStand panel_right_three;

    public SolarPanel(Block baseBlock, JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
        this.baseBlock = baseBlock;
        this.rod_one = this.baseBlock.getRelative(BlockFace.UP);
        this.rod_two = this.rod_one.getRelative(BlockFace.UP);
        this.baseLocation = Utils.center(this.rod_one.getRelative(BlockFace.UP).getLocation())
                .subtract(0, 0.45, 0);

        this.create();
        this.spawn();
    }

    private void rotate(float degrees) {
        //TODO
    }

    private void create() {
        this.baseBlock.setType(Material.OBSIDIAN);
        this.rod_one.setType(Material.END_ROD);
        this.rod_two.setType(Material.END_ROD);

        BlockState rod_two_state = this.rod_two.getState();
        rod_two_state.setData(new MaterialData(Material.END_ROD));
        rod_two_state.update(true);
    }

    private void spawn() {
        // Spawn the middle row of entities
        this.panel_middle_two = this.baseLocation.getWorld().spawn(this.baseLocation, ArmorStand.class);
        this.setup(this.panel_middle_two);
        this.panel_middle_three = this.baseLocation.getWorld().spawn(this.panel_middle_two.getLocation().add(.6, 0, 0),
                ArmorStand.class);
        this.setup(this.panel_middle_three);
        this.panel_middle_one = this.baseLocation.getWorld().spawn(this.panel_middle_two.getLocation().add(-.6, 0, 0),
                ArmorStand.class);
        this.setup(this.panel_middle_one);

        // Spawn the left row of entities
        this.panel_left_one = this.baseLocation.getWorld().spawn(this.panel_middle_three.getLocation().add(0, 0, -.6),
                ArmorStand.class);
        this.setup(this.panel_left_one);
        this.panel_left_two = this.baseLocation.getWorld().spawn(this.panel_middle_two.getLocation().add(0, 0, -.6),
                ArmorStand.class);
        this.setup(this.panel_left_two);
        this.panel_left_three = this.baseLocation.getWorld().spawn(this.panel_middle_one.getLocation().add(0, 0, -.6),
                ArmorStand.class);
        this.setup(this.panel_left_three);

        // Spawn the right row of entities
        this.panel_right_one = this.baseLocation.getWorld().spawn(this.panel_middle_three.getLocation().add(0, 0, .6),
                ArmorStand.class);
        this.setup(this.panel_right_one);
        this.panel_right_two = this.baseLocation.getWorld().spawn(this.panel_middle_two.getLocation().add(0, 0, .6),
                ArmorStand.class);
        this.setup(this.panel_right_two);
        this.panel_right_three = this.baseLocation.getWorld().spawn(this.panel_middle_one.getLocation().add(0, 0, .6),
                ArmorStand.class);
        this.setup(this.panel_right_three);
    }

    private void setup(ArmorStand armorStand) {
        armorStand.setRemoveWhenFarAway(false);
        armorStand.setBasePlate(false);
        armorStand.setGravity(false);
        armorStand.setVisible(false);
        armorStand.setHelmet(new ItemStack(Material.CARPET, 1, (byte) 9));
    }
}