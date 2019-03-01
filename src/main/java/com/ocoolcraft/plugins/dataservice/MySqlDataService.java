package com.ocoolcraft.plugins.dataservice;

import com.ocoolcraft.plugins.model.HopperChunk;
import com.ocoolcraft.plugins.model.HopperPlayer;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlDataService implements DataService {

    private final static String CONFIG_FILE = "config.json";
    private Connection databaseConnection;
    private JSONObject configJSON;

    public MySqlDataService(JSONObject _configJSON) {
        try {
            this.configJSON = _configJSON;
            String dataBaseURL = configJSON.getString("dataBaseURL");
            String userName = configJSON.getString("userName");
            String password = configJSON.getString("password");
            databaseConnection = DriverManager.getConnection(dataBaseURL, userName, password);
            if (databaseConnection != null) {

            }
        } catch (Exception ex) {
            throw new RuntimeException("error: " + ex);
        }
    }

    @Override
    public HopperPlayer getPlayer(String playername) {
        return null;
    }

    @Override
    public HopperChunk getChunk(String playername, String world, int x, int z) {
        return null;
    }

    @Override
    public void updateChunk(HopperPlayer player,HopperChunk hopperChunk) {

    }

    public void setStandardLimit(int limit) {
        try {
            configJSON.put("standardLimit",limit);
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
            databaseConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
