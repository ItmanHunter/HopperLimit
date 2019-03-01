package com.ocoolcraft.plugins;

import com.ocoolcraft.plugins.dataservice.DataService;
import com.ocoolcraft.plugins.model.HopperChunk;
import com.ocoolcraft.plugins.model.HopperPlayer;
import com.ocoolcraft.plugins.utils.HopperLimitUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class HopperLimitListener implements Listener {

    private DataService dataService;

    @EventHandler
    public void onHopperPlace(BlockPlaceEvent event) {
        if ( event.getBlockPlaced().getType() == Material.HOPPER) {
            Player player = event.getPlayer();
            HopperPlayer hopperPlayer = dataService.getPlayer(player.getUniqueId().toString());
            HopperChunk hopperChunk = HopperLimitUtil.getHopperChunk(event.getBlockPlaced(),player,dataService);
            if (HopperLimitUtil.isHopperLimit(hopperChunk,player,dataService)) {
                event.setCancelled(true);
                player.sendMessage("Hopper limit has been reached!!!");
            } else {
                hopperChunk.increaseCount();
                dataService.updateChunk(hopperPlayer,hopperChunk);
                player.sendMessage("Placed hopper successfully!! Total now: " + hopperChunk.getCount() + " Placed at chunk " + hopperChunk.getHopperPos() + " by " + player.getUniqueId().toString());
            }
        }
    }

    @EventHandler
    public void onHopperBreak(BlockBreakEvent event) {
        if ( event.getBlock().getType() == Material.HOPPER) {
            Player player = event.getPlayer();
            HopperPlayer hopperPlayer = dataService.getPlayer(player.getUniqueId().toString());
            HopperChunk hopperChunk = HopperLimitUtil.getHopperChunk(event.getBlock(),player,dataService);
            hopperChunk.decreaseCount();
            player.sendMessage("Removed hopper successfully!! Total now: " + hopperChunk.getCount());
            dataService.updateChunk(hopperPlayer,hopperChunk);
        }
    }

    public void setDataService(DataService dataService) {
        this.dataService = dataService;
    }
}
