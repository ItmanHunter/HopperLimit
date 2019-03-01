package com.ocoolcraft.plugins.service.data;

import com.ocoolcraft.plugins.models.ChunkModel;
import com.ocoolcraft.plugins.models.HopperModel;
import com.ocoolcraft.plugins.models.PlayerModel;

public interface DataService {
    void load();
    ChunkModel getChunkModel(String chunkId);
    HopperModel getHopperModel(String hopperId);
    PlayerModel getPlayerModel(String playerId, String playername);
    void addHopper(HopperModel hopperModel);
    int getPlayerHopperCount(String playerId, String chunkId);
    int getHopperLimit();
    void setHopperLimit(int limit);
    void removeHopper(HopperModel hopperModel);
    void unload();
}
