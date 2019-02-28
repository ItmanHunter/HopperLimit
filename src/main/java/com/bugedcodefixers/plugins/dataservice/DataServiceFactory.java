package com.bugedcodefixers.plugins.dataservice;

import com.bugedcodefixers.plugins.config.ConfigLoader;
import com.bugedcodefixers.plugins.utils.HopperLimitUtil;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.JSONException;
import org.json.JSONObject;

public class DataServiceFactory {


    public static DataService getDataService() {
        try {
            String configJsonString = ConfigLoader.getConfigString();
            JSONObject configJson = new JSONObject(configJsonString);
            String dataServiceType = "json";
            if (configJson.has("json")) {
                dataServiceType = configJson.getString("dataServiceType");
            }
            if (dataServiceType.equalsIgnoreCase("mysql")) {
                return new MySqlDataService(configJson);
            } else {
                return new JSONService();
            }
        } catch (Exception e) {
            throw new RuntimeException("error: "+ e);
        }

    }
}
