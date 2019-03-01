package com.ocoolcraft.plugins.service.data;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class FileDataServiceTest {

    private DataService dataService;

    private void setUp() {
        String location = "testLocation/";
        File file = new File(location);
        if (!file.exists()) {
            file.mkdirs();
        }
        dataService = new FileDataService(file.getAbsolutePath());
        dataService.load();;
    }

    @Test
    public void load() {
        setUp();
        dataService.setHopperLimit(10);
        assert dataService.getHopperLimit() == 10;
    }
}