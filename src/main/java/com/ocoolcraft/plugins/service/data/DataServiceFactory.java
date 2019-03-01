package com.ocoolcraft.plugins.service.data;

import java.io.File;

public class DataServiceFactory {

    public static DataService getDataService(File dataFolder) {
        DataService dataService = new FileDataService(dataFolder.getAbsolutePath());
        dataService.load();
        return dataService;
    }

}
