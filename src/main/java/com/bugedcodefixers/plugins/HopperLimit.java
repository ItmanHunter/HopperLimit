package com.bugedcodefixers.plugins;

import com.bugedcodefixers.plugins.dataservice.DataService;
import com.bugedcodefixers.plugins.dataservice.DataServiceFactory;
import com.bugedcodefixers.plugins.dataservice.MySqlDataService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class HopperLimit extends JavaPlugin {

    private DataService dataService;

    private void setupDataService() {
        dataService = DataServiceFactory.getDataService();
    }

    @Override
    public void onEnable() {
        setupDataService();
        HopperLimitListener hopperLimitListener = new HopperLimitListener();
        hopperLimitListener.setDataService(dataService);
        getServer().getPluginManager().registerEvents(hopperLimitListener, this);
        getLogger().info("Enabled hopperlimit");
    }

    @Override
    public void onDisable(){
        dataService.unloadDataService();
        getLogger().info("disabled hopperlimit");
    }

    @SuppressWarnings("deprecation")
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
                dataService.setStandardLimit(limit);
            }
        }
        return true;
    }

}
