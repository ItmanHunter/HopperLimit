package com.ocoolcraft.plugins.dataservice;

import com.ocoolcraft.plugins.model.HopperChunk;
import com.ocoolcraft.plugins.model.HopperPlayer;

public interface DataService {
    HopperPlayer getPlayer(String playername);
    HopperChunk getChunk(String playername, String world, int x, int z);
    void updateChunk(HopperPlayer player,HopperChunk hopperChunk);
    int getStandardLimit();
    void unloadDataService();
    void setStandardLimit(int limit);
}
