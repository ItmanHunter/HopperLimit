package com.ocoolcraft.plugins.utils;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class HopperLimitUtil {

    public static Boolean isHopperLimit(int count,Player player,int standardLimit) {
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
        return (count + 1 > mul*standardLimit);
    }

    public static String getPositionString(Block blockPlaced) {
        return blockPlaced.getWorld().getEnvironment().name() + ":" + blockPlaced.getX() + ":" + blockPlaced.getY() + ":" + blockPlaced.getZ();
    }

    public static String getPositionStringForChunk(Block blockPlaced) {
        return blockPlaced.getWorld().getEnvironment().name() + ":" + (blockPlaced.getX() - blockPlaced.getX()%16) + ":" +  (blockPlaced.getZ() - blockPlaced.getZ()%16);
    }

}
