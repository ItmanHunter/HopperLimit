package com.bugedcodefixers.plugins.dataservice;

import com.bugedcodefixers.plugins.HopperLimit;
import com.bugedcodefixers.plugins.config.ConfigLoader;
import com.bugedcodefixers.plugins.model.HopperChunk;
import com.bugedcodefixers.plugins.model.HopperPlayer;
import com.bugedcodefixers.plugins.model.ModelAdaptor;
import com.bugedcodefixers.plugins.utils.HopperLimitUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

public class FileDataService implements DataService {

    public static String PLAYERS_DATA_JSON = "players.json";

    public static JSONObject players;

    private void load() throws Exception{
        players = new JSONObject(HopperLimitUtil.readFromFile(ConfigLoader.dataFolder.getAbsolutePath() +
                File.separator + PLAYERS_DATA_JSON));
    }

    private void save() throws Exception {
        HopperLimitUtil.saveStringToFile(players.toString(),ConfigLoader.dataFolder.getAbsolutePath() +
                File.separator + PLAYERS_DATA_JSON);
    }

    public FileDataService() {
        try {
            load();
        } catch (Exception ex) {
            throw new RuntimeException("error: " + ex);
        }
    }

    @Override
    public HopperPlayer getPlayer(String playername) {
        try {
            if (!players.has(playername)) {
                JSONObject newPlayer = new JSONObject();
                newPlayer.put("name", playername);
                players.put(playername,newPlayer);
                save();
            }
            JSONObject player = players.getJSONObject(playername);
            return ModelAdaptor.getPlayerFromJSON(player);
        } catch (Exception ex) {
            throw new RuntimeException("error: " + ex);
        }
    }

    @Override
    public HopperChunk getChunk(String playername, String world, int x, int z) {
        try {
            String chunkPath = ConfigLoader.dataFolder.getAbsolutePath() +
                    File.separator + "chunks" + File.separator + playername + ".json";
            File file = new File(chunkPath);
            if (!file.exists()) {
                getPlayer(playername);
                file.getParentFile().mkdirs();
                file.createNewFile();
                HopperLimitUtil.saveStringToFile("{}",file.getAbsolutePath());
            }
            JSONObject playerchunks = new JSONObject(HopperLimitUtil.readFromFile(file.getAbsolutePath()));
            String pos = world + ":" + x + ":" + z;
            if (!playerchunks.has(pos)) {
                JSONObject newChunk = new JSONObject();
                newChunk.put("hopperPos", pos);
                newChunk.put("count", 0);
                playerchunks.put(pos, newChunk);
            }
            return ModelAdaptor.getChunkFromJSON(playerchunks.getJSONObject(pos));
        }catch (Exception e) {
            throw new RuntimeException("error: " + e);
        }
    }

    @Override
    public void updateChunk(HopperPlayer player, HopperChunk hopperChunk) {
        try {
            String chunkPath = ConfigLoader.dataFolder.getAbsolutePath() +
                    File.separator + "chunks" + File.separator + player.getPlayerListName() + ".json";
            File file = new File(chunkPath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
                HopperLimitUtil.saveStringToFile("{}",file.getAbsolutePath());
            }
            JSONObject playerchunks = new JSONObject(HopperLimitUtil.readFromFile(file.getAbsolutePath()));
            JSONObject newChunk = new JSONObject();
            newChunk.put("hopperPos", hopperChunk.getHopperPos());
            newChunk.put("count", hopperChunk.getCount());
            playerchunks.put(hopperChunk.getHopperPos(), newChunk);
            HopperLimitUtil.saveStringToFile(playerchunks.toString(),file.getAbsolutePath());
        }catch (Exception e) {
            throw new RuntimeException("error: " + e);
        }
    }

    @Override
    public int getStandardLimit() {
        try {
            return players.getInt("StandardLimit");
        } catch (JSONException e) {
            return 10;
        }
    }

    @Override
    public void unloadDataService() {
        try {
            save();
        } catch (Exception e) {
            e.printStackTrace();;
        }
    }

    @Override
    public void setStandardLimit(int limit) {
        try {
            players.put("StandardLimit", limit);
        } catch (JSONException e) {
            e.printStackTrace();;
        }
    }
}