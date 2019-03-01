package com.ocoolcraft.plugins.config;

import com.ocoolcraft.plugins.utils.HopperLimitUtil;

import java.io.File;

public class ConfigLoader {

    public final static String CONFIG_FILE = "hopper_limit_config.json";

    public static File dataFolder;

    public static void loadDataFolder(File _dataFolder) {
        dataFolder = _dataFolder;
    }

    public static String getConfigString() {
        File file = new File(dataFolder,CONFIG_FILE);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        return HopperLimitUtil.readFromFile(file.getAbsolutePath());
    }

    public static void saveConfig(String config) {
        File file = new File(dataFolder,CONFIG_FILE);
        HopperLimitUtil.saveStringToFile(config,file.getAbsolutePath());;
    }

}
