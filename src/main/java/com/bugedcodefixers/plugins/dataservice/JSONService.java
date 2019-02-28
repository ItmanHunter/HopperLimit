package com.bugedcodefixers.plugins.dataservice;

import com.bugedcodefixers.plugins.model.HopperChunk;
import com.bugedcodefixers.plugins.model.HopperPlayer;
import com.bugedcodefixers.plugins.model.ModelAdaptor;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONService implements DataService {

    private JSONObject configJSON = null;
    private JSONObject players, chunks;

    public JSONService(JSONObject _configJSON) {
        try {
            this.configJSON = _configJSON;
            if (!configJSON.has("players")) {
                configJSON.put("players", new JSONObject());
            }
            if (!configJSON.has("chunks")) {
                configJSON.put("chunks", new JSONObject());
            }
            players = configJSON.optJSONObject("players");
            chunks = configJSON.getJSONObject("chunks");
        } catch (Exception e) {
            throw new RuntimeException("error: " + e);
        }
    }

    @Override
    public HopperPlayer getPlayer(String playername) {
        try {
            JSONObject player = null;
            if (!players.has(playername)) {
                JSONObject newplayer = new JSONObject();
                newplayer.put("name", playername);
                players.put(playername, newplayer);
            }
            player = players.getJSONObject(playername);
            return ModelAdaptor.getPlayerFromJSON(player);
        } catch (JSONException e) {
            throw new RuntimeException("error: " + e);
        }
    }

    @Override
    public HopperChunk getChunk(String playername, String world, int x, int z) {
        try {
            if (!chunks.has(playername)) {
                getPlayer(playername);
                chunks.put(playername, new JSONObject());
            }
            JSONObject playerchunks = chunks.getJSONObject(playername);
            String pos = world + ":" + x + ":" + z;
            if (!playerchunks.has(pos)) {
                JSONObject newChunk = new JSONObject();
                newChunk.put("hopperPos", pos);
                playerchunks.put(pos,newChunk);
            }
            JSONObject chunk = playerchunks.getJSONObject(pos);
            return ModelAdaptor.getChunkFromJSON(chunk);
        } catch (Exception e) {
            throw new RuntimeException("error: " + e);
        }
    }

    @Override
    public void updateChunk(HopperPlayer player, HopperChunk hopperChunk) {
        try {
            if (!chunks.has(player.getName())) {
                chunks.put(player.getName(), new JSONObject());
            }
            JSONObject playerchunks = chunks.getJSONObject(player.getName());
            JSONObject newChunk = new JSONObject();
            newChunk.put("hopperPos", hopperChunk.getHopperPos());
            newChunk.put("count", hopperChunk.getCount());
            playerchunks.put(hopperChunk.getHopperPos(), newChunk);
        } catch (Exception e) {
            throw new RuntimeException("error: " + e);
        }
    }

    @Override
    public void setStandardLimit(int limit) {
        try {
            configJSON.put("standardLimit", limit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getStandardLimit() {
        try {
            return configJSON.getInt("standardLimit");
        } catch (Exception e) {
            e.printStackTrace();
            return 10;
        }
    }

    @Override
    public void unloadDataService() {
        try {

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
