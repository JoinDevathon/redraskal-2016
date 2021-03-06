package org.devathon.contest2016;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Redraskal_2 on 11/5/2016.
 */
public class SolarPanel implements Listener {

    private static List<SolarPanel> LOADED_PANELS = new ArrayList<SolarPanel>();

    public static void removeAll() {
        for(SolarPanel solarPanel : LOADED_PANELS) {
            solarPanel.remove(true);
        }
        LOADED_PANELS.clear();
    }

    private JavaPlugin javaPlugin;
    private SolarPanel instance;
    private Location baseLocation;
    private UUID uuid;
    private Block baseBlock;
    private Block rod_one;
    private ArmorStand panel;
    private boolean loaded = false;

    public SolarPanel(Block baseBlock, UUID uuid, JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
        this.instance = this;
        this.uuid = uuid;
        this.baseBlock = baseBlock;
        this.rod_one = this.baseBlock.getRelative(BlockFace.UP);
        this.baseLocation = Utils.center(this.baseBlock.getRelative(BlockFace.UP).getLocation())
                .subtract(0, 0.40, 0);

        this.create();
        if(getNearbyPlayers() > 0) { this.spawn(); }
        this.javaPlugin.getServer().getPluginManager().registerEvents(this, this.javaPlugin);
        LOADED_PANELS.add(this);
    }

    @EventHandler
    public void onChunkChange(PlayerMoveEvent playerMoveEvent) {
        if(playerMoveEvent.getTo().getChunk() != playerMoveEvent.getFrom().getChunk()) {
            if(playerMoveEvent.getTo().getChunk() == baseLocation.getChunk()) {
                if(!this.isLoaded()) {
                    this.spawn();
                }
            } else if(playerMoveEvent.getFrom().getChunk() == baseLocation.getChunk()) {
                if(getNearbyPlayers() <= 0) {
                    if(this.isLoaded()) {
                        this.despawn();
                    }
                }
            }
        }
    }

    @EventHandler
    public void onArmorStandManipulate(PlayerArmorStandManipulateEvent playerArmorStandManipulateEvent) {
        if(playerArmorStandManipulateEvent.getRightClicked().hasMetadata("solarPanel_" + this.uuid.toString())) {
            playerArmorStandManipulateEvent.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent blockBreakEvent) {
        if(blockBreakEvent.getBlock().hasMetadata("solarPanel_" + this.uuid.toString())) {
            this.remove(false);
            try {
                Utils.remove(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void remove(boolean stayInGlobalList) {
        this.baseBlock.setType(Material.AIR);
        this.rod_one.setType(Material.AIR);
        this.despawn();
        HandlerList.unregisterAll(this);
        if(!stayInGlobalList) { LOADED_PANELS.remove(this); }
    }

    public void despawn() {
        if(this.loaded) {
            this.loaded = false;
            this.panel.remove();
        }
    }

    public boolean isLoaded() {
        return this.loaded;
    }

    public int getNearbyPlayers() {
        int players = 0;
        for(Entity entity : this.baseLocation.getChunk().getEntities()) {
            if(entity instanceof Player) {
                players++;
            }
        }
        return players;
    }

    public Location getBaseLocation() {
        return this.baseLocation;
    }

    public Block getBaseBlock() {
        return this.baseBlock;
    }

    private void auto() {
        new BukkitRunnable() {
            public void run() {
                if(panel.isDead()) {
                    this.cancel();
                } else {
                    if(baseLocation.getWorld().getGameRuleValue("doDaylightCycle").equalsIgnoreCase("true")) {
                        rotate(Angle.calc(baseLocation.getWorld()));
                    }
                }
            }
        }.runTaskTimer(javaPlugin, 0, 10L);

        new BukkitRunnable() {
            public void run() {
                if(panel.isDead()) {
                    this.cancel();
                } else {
                    int percent = Angle.percent(baseLocation.getWorld());
                    if(percent <= 105) {
                        Utils.power(instance, true);
                    } else {
                        Utils.power(instance, false);
                    }
                }
            }
        }.runTaskTimer(javaPlugin, 0, 20L);
    }

    private void rotate(float degrees) {
        this.panel.setHeadPose(new EulerAngle(0, 0, degrees));
    }

    private void create() {
        this.baseBlock.setType(Material.OBSIDIAN);
        this.rod_one.setType(Material.END_ROD);

        this.baseBlock.setMetadata("solarPanel_" + uuid.toString(), new FixedMetadataValue(this.javaPlugin, true));
        this.rod_one.setMetadata("solarPanel_" + uuid.toString(), new FixedMetadataValue(this.javaPlugin, true));
    }

    public void spawn() {
        this.loaded = true;
        this.panel = this.baseLocation.getWorld().spawn(this.baseLocation, ArmorStand.class);
        this.setup(this.panel);
        this.auto();
    }

    private void setup(ArmorStand armorStand) {
        armorStand.setRemoveWhenFarAway(false);
        armorStand.setBasePlate(false);
        armorStand.setGravity(false);
        armorStand.setVisible(false);
        armorStand.setHelmet(new ItemStack(Material.CARPET, 1, (byte) 9));
        armorStand.setMetadata("solarPanel_" + uuid.toString(), new FixedMetadataValue(this.javaPlugin, true));
    }
}