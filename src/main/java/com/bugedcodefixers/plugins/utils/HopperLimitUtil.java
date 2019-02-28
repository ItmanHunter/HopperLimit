package com.bugedcodefixers.plugins.utils;

import com.bugedcodefixers.plugins.dataservice.DataService;
import org.apache.commons.io.FileUtils;
import org.bukkit.World;
import org.bukkit.block.Block;
import com.bugedcodefixers.plugins.model.HopperChunk;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;

public class HopperLimitUtil {


    public static String readFromFile(String filename) {
        try {
            return FileUtils.readFileToString(new File(filename), Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
            return "{}";
        }
    }

    public static void saveStringToFile(String stringData,String filename) {
        try {
            FileUtils.writeStringToFile(new File(filename),stringData,Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException("error in config file " + e);
        }
    }

    public static HopperChunk getHopperChunk(Block placedBlock, Player player, DataService dataService) {
        if ( placedBlock == null || dataService == null) {
            return null;
        }
        int startX = placedBlock.getX() - placedBlock.getX()%16;
        int startZ = placedBlock.getZ() - placedBlock.getZ()%16;
        String worldName = placedBlock.getWorld().getEnvironment().name();
        return dataService.getChunk(player.getDisplayName(),
                worldName,
                startX,
                startZ);
    }

    public static Boolean isHopperLimit(HopperChunk hopperChunk,Player player,DataService dataService) {
        int mul = 1;
        if (player.hasPermission("hopperlimit.double")) {
            mul = 2;
        }
        if (player.hasPermission("hopperlimit.triple")) {
            mul = 3;
        }
        if (player.hasPermission("hopperlimit.quadruple")) {
            mul = 4;
        }
        return (hopperChunk.getCount() + 1 > mul*dataService.getStandardLimit());
    }

}
