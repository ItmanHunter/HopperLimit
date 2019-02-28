package com.bugedcodefixers.plugins.dataservice;

import com.bugedcodefixers.plugins.utils.HopperLimitUtil;
import org.json.JSONException;
import org.json.JSONObject;

public class DataServiceFactory {

    private final static String CONFIG_FILE = "config.json";

    public static DataService getDataService() {
        try {
            String configJsonString = HopperLimitUtil.readFromFile(CONFIG_FILE);
            JSONObject configJson = new JSONObject(configJsonString);
            String dataServiceType = configJson.getString("dataServiceType");
            if (dataServiceType.equalsIgnoreCase("mysql")) {
                return new MySqlDataService(configJson);
            } else {
                return new JSONService(configJson);
            }
        } catch (Exception e) {
            throw new RuntimeException("error: "+ e);
        }

    }
}
