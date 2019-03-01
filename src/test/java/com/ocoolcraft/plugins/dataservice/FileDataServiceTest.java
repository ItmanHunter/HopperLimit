package com.ocoolcraft.plugins.dataservice;

import com.ocoolcraft.plugins.config.ConfigLoader;
import com.ocoolcraft.plugins.model.HopperChunk;
import com.ocoolcraft.plugins.model.HopperPlayer;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class FileDataServiceTest {

    private DataService dataService;

    public void setup() throws Exception {
        String testLocation = "testLocation/";
        File testFile = new File(testLocation);
        ConfigLoader.loadDataFolder(testFile);
        dataService = DataServiceFactory.getDataService();
    }

    @Test
    public void getPlayer() throws Exception {
        setup();
        String playername = "UDID-DDDD-ASDAS-SDASD";
        HopperPlayer player = dataService.getPlayer(playername);
        assert player != null;
        assert player.getPlayerListName().equals(playername);
    }

    @Test
    public void checkChunk() throws Exception {

        String playername = "UDD1-DD2D-ASDAS-SDASD";
        String chunkpos = "world:10:-10";
        for(int count=0;count < 5;count++) {
            setup();
            HopperChunk chunk = dataService.getChunk(playername,"world",10,-10);
            assert chunk != null;
            System.out.println("hoppers: " +  chunk.getCount() + " count: " + count);
            assert chunk.getCount() == count;
            chunk.increaseCount();
            dataService.updateChunk(dataService.getPlayer(playername),chunk);
            HopperChunk updatedchunk = dataService.getChunk(playername,"world",10,-10);
            assert updatedchunk != null;
            assert updatedchunk.getCount() == count+1;
        }
    }

    @Test
    public void testStandardLimit() throws Exception {

    }

}