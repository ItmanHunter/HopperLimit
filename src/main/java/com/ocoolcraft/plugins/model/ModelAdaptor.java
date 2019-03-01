package com.ocoolcraft.plugins.model;

import org.json.JSONException;
import org.json.JSONObject;

public class ModelAdaptor {

    public static HopperPlayer getPlayerFromJSON(JSONObject player) {
        HopperPlayer hopperPlayer = new HopperPlayer();
        try {
            hopperPlayer.setName(player.getString("name"));
            if (player.has("id")){
                hopperPlayer.setId(player.getInt("id"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return hopperPlayer;
    }

    public static HopperChunk getChunkFromJSON(JSONObject chunk) {
        HopperChunk hopperChunk = new HopperChunk();
        try {
            hopperChunk.setHopperPos(chunk.getString("hopperPos"));
            if (chunk.has("id")){
                hopperChunk.setId(chunk.getInt("id"));
            }
            if (chunk.has("playerId")){
                hopperChunk.setPlayerId(chunk.getInt("playerId"));
            }
            if (chunk.has("count")){
                hopperChunk.setCount(chunk.getInt("count"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return hopperChunk;
    }

    public static JSONObject chunkToJson(HopperChunk hopperChunk) {
        try {
            JSONObject chunkJson = new JSONObject();
            chunkJson.put("hopperPos", hopperChunk.getHopperPos());
            chunkJson.put("count", hopperChunk.getCount());
            return chunkJson;
        } catch (Exception e) {
            throw new RuntimeException("error: " + e);
        }
    }

    public static JSONObject playerToJson(HopperPlayer hopperPlayer) {
        try {
            JSONObject playerJson = new JSONObject();
            playerJson.put("name", hopperPlayer.getPlayerListName());
            return playerJson;
        } catch (Exception e) {
            throw new RuntimeException("error: " + e);
        }
    }
}
