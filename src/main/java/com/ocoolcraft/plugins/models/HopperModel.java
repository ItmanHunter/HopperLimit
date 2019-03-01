package com.ocoolcraft.plugins.models;

public class HopperModel {
    private String id, playerId, chunkId;

    public String getId() {
        return id;
    }

    public HopperModel(String id, String playerId, String chunkId) {
        this.id = id;
        this.playerId = playerId;
        this.chunkId = chunkId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getChunkId() {
        return chunkId;
    }

    public void setChunkId(String chunkId) {
        this.chunkId = chunkId;
    }
}
