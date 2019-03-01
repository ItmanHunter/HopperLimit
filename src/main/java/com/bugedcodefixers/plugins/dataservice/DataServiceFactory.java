package com.bugedcodefixers.plugins.dataservice;

import com.bugedcodefixers.plugins.config.ConfigLoader;
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
                return new FileDataService();
            }
        } catch (Exception e) {
            throw new RuntimeException("error: "+ e);
        }

    }
}
