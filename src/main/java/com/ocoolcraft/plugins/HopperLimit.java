package com.ocoolcraft.plugins;

import com.ocoolcraft.plugins.service.data.DataService;
import com.ocoolcraft.plugins.service.data.DataServiceFactory;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class HopperLimit extends JavaPlugin {

    private DataService dataService;

    @Override
    public void onEnable() {
        dataService = DataServiceFactory.getDataService(getDataFolder());
        HopperLimitListener hopperLimitListener = new HopperLimitListener();
        hopperLimitListener.setDataService(dataService);
        getServer().getPluginManager().registerEvents(hopperLimitListener, this);
        getLogger().info("Enabled hopperlimit");
    }

    @Override
    public void onDisable() {
        dataService.unload();
        getLogger().info("Disabled hopperlimit");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command0, String label, String agrs[]) {
        if (sender instanceof Player) {
            if (label.equalsIgnoreCase("sethopperlimit")) {
                if (agrs.length < 1) {
                    Player player = (Player) sender;
                    player.sendMessage("Usage /sethopperlimit <limitno>");
                    return true;
                }
                int limit = Integer.parseInt(agrs[0]);
                dataService.setHopperLimit(limit);
                getServer().broadcastMessage(ChatColor.RED + "The hopper limit is " + limit);
            }
        }
        return true;
    }
}
