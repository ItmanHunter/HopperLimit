package com.bugedcodefixers.plugins.dataservice;

import com.bugedcodefixers.plugins.config.ConfigLoader;
import com.bugedcodefixers.plugins.model.HopperChunk;
import com.bugedcodefixers.plugins.model.HopperPlayer;
import com.bugedcodefixers.plugins.model.ModelAdaptor;
import com.bugedcodefixers.plugins.utils.HopperLimitUtil;
import org.json.JSONException;
import org.json.JSONObject;


public class JSONService implements DataService {

    private JSONObject configJSON = null;
    private JSONObject players, chunks;

    private void load() throws JSONException {
        String configJsonString = ConfigLoader.getConfigString();
        this.configJSON  = new JSONObject(configJsonString);
        if (!configJSON.has("players")) {
            configJSON.put("players", new JSONObject());
        }
        if (!configJSON.has("chunks")) {
            configJSON.put("chunks", new JSONObject());
        }
        players = configJSON.optJSONObject("players");
        chunks = configJSON.getJSONObject("chunks");
    }

    public JSONService() {
        try {
            load();
        } catch (Exception e) {
            throw new RuntimeException("error: " + e);
        }
    }

    private void createNewPlayer(String playername) throws Exception {
        JSONObject newplayer = new JSONObject();
        newplayer.put("name", playername);
        players.put(playername, newplayer);
        save();
    }

    @Override
    public HopperPlayer getPlayer(String playername) {
        try {
            load();
            JSONObject player = null;
            if (!players.has(playername)) {
                createNewPlayer(playername);
            }
            player = players.getJSONObject(playername);
            return ModelAdaptor.getPlayerFromJSON(player);
        } catch (Exception e) {
            throw new RuntimeException("error: " + e);
        }
    }

    @Override
    public HopperChunk getChunk(String playername, String world, int x, int z) {
        try {
            load();
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
                save();
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
            load();
            if (!chunks.has(player.getPlayerListName())) {
                chunks.put(player.getPlayerListName(), new JSONObject());
            }
            JSONObject playerchunks = chunks.getJSONObject(player.getPlayerListName());
            JSONObject newChunk = new JSONObject();
            newChunk.put("hopperPos", hopperChunk.getHopperPos());
            newChunk.put("count", hopperChunk.getCount());
            playerchunks.put(hopperChunk.getHopperPos(), newChunk);
            save();
        } catch (Exception e) {
            throw new RuntimeException("error: " + e);
        }
    }

    @Override
    public void setStandardLimit(int limit) {
        try {
            load();
            configJSON.put("standardLimit", limit);
            save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getStandardLimit() {
        try {
            load();
            return configJSON.getInt("standardLimit");
        } catch (Exception e) {
            e.printStackTrace();
            return 10;
        }
    }

    @Override
    public void unloadDataService() {
        try {
            save();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void save() throws Exception {
        configJSON.put("chunks",chunks);
        configJSON.put("players",players);
        ConfigLoader.saveConfig(configJSON.toString());
    }
}
