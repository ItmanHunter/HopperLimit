package com.bugedcodefixers.plugins.model;

public class HopperChunk {

    private int id;

    private int playerId;

    private int count;

    private String hopperPos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getHopperPos() {
        return hopperPos;
    }

    public void setHopperPos(String hopperPos) {
        this.hopperPos = hopperPos;
    }

    private String[] posparts() {
        return getHopperPos().split(":");
    }

    public void setWorld(String world) {
        String[] parts = posparts();
        hopperPos = world + ":" + parts[1] + ":" + parts[2];
    }

    public void setX(int x) {
        String[] parts = posparts();
        hopperPos = parts[0] + ":" + x + ":" + parts[2];
    }

    public void setZ(int z) {
        String[] parts = posparts();
        hopperPos = parts[0] + ":" + parts[1] + ":" + z;
    }

    public String getWorld() {
        return posparts()[0];
    }

    public int getZ() {
        return Integer.parseInt(posparts()[2]);
    }

    public int getX() {
        return Integer.parseInt(posparts()[1]);
    }

    public void increaseCount() {
        count++;
    }
}