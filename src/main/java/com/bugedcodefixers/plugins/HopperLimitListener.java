package com.bugedcodefixers.plugins;

import com.bugedcodefixers.plugins.dataservice.DataService;
import com.bugedcodefixers.plugins.model.HopperChunk;
import com.bugedcodefixers.plugins.model.HopperPlayer;
import com.bugedcodefixers.plugins.utils.HopperLimitUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

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
                player.sendMessage("Placed hopper successfully!! Total now: " + hopperChunk.getCount() + " Placed at chunk " + hopperChunk.getHopperPos() + " by " + player.getUniqueId().toString());
                dataService.updateChunk(hopperPlayer,hopperChunk);
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
