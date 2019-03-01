package com.ocoolcraft.plugins.model;

import org.json.JSONObject;

public class HopperPlayer {

    private long id;

    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlayerListName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
