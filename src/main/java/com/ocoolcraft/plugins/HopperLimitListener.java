package com.ocoolcraft.plugins;

import com.ocoolcraft.plugins.models.ChunkModel;
import com.ocoolcraft.plugins.models.HopperModel;
import com.ocoolcraft.plugins.models.PlayerModel;
import com.ocoolcraft.plugins.service.data.DataService;
import com.ocoolcraft.plugins.utils.HopperLimitUtil;
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
            PlayerModel playerModel = dataService.getPlayerModel(
                    player.getUniqueId().toString(),player.getName());
            ChunkModel chunkModel = dataService.getChunkModel(
              HopperLimitUtil.getPositionStringForChunk(event.getBlockPlaced())
            );
            String hopperId = HopperLimitUtil.getPositionString(event.getBlockPlaced());
            HopperModel hopperModel = new HopperModel(hopperId,playerModel.getId(),chunkModel.getId());
            int count = dataService.getPlayerHopperCount(playerModel.getId(),chunkModel.getId());
            if (HopperLimitUtil.isHopperLimit(count,player,dataService.getHopperLimit())) {
                event.setCancelled(true);
                player.sendMessage("Hopper limit has been reached!!!");
            } else {
                dataService.addHopper(hopperModel);
                player.sendMessage("Placed hopper successfully!! Total at chunk now: " + (count + 1));
            }
        }
    }

    @EventHandler
    public void onHopperBreak(BlockBreakEvent event) {
        if ( event.getBlock().getType() == Material.HOPPER) {
            Player player = event.getPlayer();
            PlayerModel playerModel = dataService.getPlayerModel(
                    player.getUniqueId().toString(),player.getName());
            ChunkModel chunkModel = dataService.getChunkModel(
                    HopperLimitUtil.getPositionStringForChunk(event.getBlock())
            );
            String hopperId = HopperLimitUtil.getPositionString(event.getBlock());
            HopperModel hopperModel = new HopperModel(hopperId,playerModel.getId(),chunkModel.getId());
            dataService.removeHopper(hopperModel);
            player.sendMessage("Removed hopper successfully!!");
        }
    }

    public void setDataService(DataService dataService) {
        this.dataService = dataService;
    }

}
