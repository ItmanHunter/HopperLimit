package com.ocoolcraft.plugins.model;

import java.util.HashSet;
import java.util.Set;

public class HopperChunk {

    private int id;

    private int playerId;

    private int count;

    private String hopperPos;

    private Set<String> hoppers;

    public HopperChunk() {
        hoppers = new HashSet<>();
    }

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

    public void decreaseCount() {
        count = Math.max(count-1,0);
    }

    public void addHopperAt(String position) {
        if (!hoppers.contains(position)) {
            hoppers.add(position);
        }
    }

    public void removeHopperAt(String position) {
        if (hoppers.contains(position)) {
            hoppers.remove(position);
        }
    }

    public Set<String> getHoppers() {
        return hoppers;
    }
}
