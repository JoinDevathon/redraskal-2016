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

/**
 * Created by Redraskal_2 on 11/5/2016.
 */
public class SolarPanel implements Listener {

    private Location baseLocation;
    private Block baseBlock;
    private Block rod_one;
    private Block rod_two;

    private ArmorStand panel_middle_one;
    private ArmorStand panel_middle_two;
    private ArmorStand panel_middle_three;

    public SolarPanel(Block baseBlock) {
        this.baseBlock = baseBlock;
        this.rod_one = this.baseBlock.getRelative(BlockFace.UP);
        this.rod_two = this.rod_one.getRelative(BlockFace.UP);
        this.baseLocation = Utils.center(this.rod_one.getRelative(BlockFace.UP).getLocation())
                .subtract(0, 0.5, 0);

        this.create();
        this.spawn();
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
        this.panel_middle_two = this.baseLocation.getWorld().spawn(this.baseLocation, ArmorStand.class);
        this.setup(this.panel_middle_two);
        this.panel_middle_three = this.baseLocation.getWorld().spawn(this.panel_middle_two.getLocation().add(1, 0, 0),
                ArmorStand.class);
        this.setup(this.panel_middle_three);
        this.panel_middle_one = this.baseLocation.getWorld().spawn(this.panel_middle_two.getLocation().add(-1, 0, 0),
                ArmorStand.class);
        this.setup(this.panel_middle_one);
    }

    private void setup(ArmorStand armorStand) {
        armorStand.setRemoveWhenFarAway(false);
        armorStand.setBasePlate(false);
        armorStand.setGravity(false);
        armorStand.setVisible(false);
        armorStand.setHelmet(new ItemStack(Material.CARPET, 1, (byte) 9));
    }
}