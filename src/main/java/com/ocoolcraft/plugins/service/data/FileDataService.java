package com.ocoolcraft.plugins.service.data;

import com.ocoolcraft.plugins.models.ChunkModel;
import com.ocoolcraft.plugins.models.HopperModel;
import com.ocoolcraft.plugins.models.PlayerModel;

import java.io.File;
import java.sql.*;

public class FileDataService implements DataService {

    private String dataFolderPath;
    private Connection conn = null;

    private static final int LIMIT_ID = 1, DEFAULT_LIMIT = 20;

    public FileDataService(String dataFolderPath) {
        this.dataFolderPath = dataFolderPath;
        File file = new File(dataFolderPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    @Override
    public void load() {
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:" + dataFolderPath + File.separator + "main.db";
            conn = DriverManager.getConnection(url);
            createTables();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("error: " + e);
        }
    }

    private void createTables() throws Exception {
        String createChunkTable = "CREATE TABLE IF NOT EXISTS " + "hopper_limit_chunk" + " (\n"
                + "	chunkid text PRIMARY KEY\n"
                + ");";
        conn.createStatement().execute(createChunkTable);
        String createPlayerTable = "CREATE TABLE IF NOT EXISTS " + "hopper_limit_player" + " (\n"
                + "	playerid text PRIMARY KEY,\n"
                + " playername text\n"
                + " );";
        conn.createStatement().execute(createPlayerTable);
        String createLimitTable = "CREATE TABLE IF NOT EXISTS " + "hopper_limit_table" + " (\n"
                + "	limitid int PRIMARY KEY,\n"
                + " limitvalue int\n"
                + " );";
        conn.createStatement().execute(createLimitTable);
        createLimit();
        String createHopperTable = "CREATE TABLE IF NOT EXISTS " + "hopper_limit_hopper" + " (\n"
                + "	hopperid text PRIMARY KEY,\n"
                + " playerid text NOT NULL,\n"
                + " chunkid text NOT NULL,\n"
                + "	FOREIGN KEY(playerid) REFERENCES hopper_limit_player(playerid),\n"
                + "	FOREIGN KEY(chunkid) REFERENCES hopper_limit_chunk(chunkid) \n"
                + ");";
        conn.createStatement().execute(createHopperTable);
    }

    private void createLimit() throws Exception {
        try {
            String sql = "INSERT INTO hopper_limit_table (limitid,limitvalue) VALUES(?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, LIMIT_ID);
            pstmt.setInt(2, DEFAULT_LIMIT);
            pstmt.executeUpdate();
        } catch (Exception ex) {

        }
    }

    @Override
    public ChunkModel getChunkModel(String chunkId) {
        String sql = "SELECT chunkid FROM hopper_limit_chunk WHERE chunkid = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, chunkId);
            ResultSet rs = pstmt.executeQuery();
            ChunkModel chunkModel = new ChunkModel(chunkId);
            if (!rs.next()) {
                createChunkModel(chunkId);
            }
            return chunkModel;
        } catch (Exception e) {
            throw new RuntimeException("error: " + e);
        }
    }

    public void createChunkModel(String chunkId) {
        String sql = "INSERT INTO hopper_limit_chunk(chunkid) VALUES(?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, chunkId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("error: " + e);
        }
    }

    public void createPlayerModel(String playerId, String playername) {
        String sql = "INSERT INTO hopper_limit_player(playerid,playername) VALUES(?,?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, playerId);
            pstmt.setString(2, playername);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("error: " + e);
        }
    }

    @Override
    public HopperModel getHopperModel(String hopperId) {
        String sql = "SELECT hopperid,playerid,chunkid FROM hopper_limit_hopper WHERE hopperid = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, hopperId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                return null;
            }
            String playerId = rs.getString("playerid");
            String chunkId = rs.getString("chunkid");
            HopperModel hopperModel = new HopperModel(hopperId, playerId, chunkId);
            return hopperModel;
        } catch (Exception e) {
            throw new RuntimeException("error: " + e);
        }
    }

    @Override
    public PlayerModel getPlayerModel(String playerId,String playername) {
        String sql = "SELECT playerid, playername FROM hopper_limit_player WHERE playerid = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, playerId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                createPlayerModel(playerId,playername);
            } else {
                playername = rs.getString("playername");
            }
            PlayerModel playerModel = new PlayerModel(playerId,playername);
            return playerModel;
        } catch (Exception e) {
            throw new RuntimeException("error: " + e);
        }
    }

    @Override
    public void addHopper(HopperModel hopperModel) {
        try {
            String sql = "INSERT INTO hopper_limit_hopper(hopperid,playerid,chunkid) VALUES(?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, hopperModel.getId());
            pstmt.setString(2, hopperModel.getPlayerId());
            pstmt.setString(3, hopperModel.getChunkId());
            pstmt.executeUpdate();
        } catch (Exception ex) {
            throw new RuntimeException("error: " + ex);
        }
    }

    @Override
    public int getPlayerHopperCount(String playerId,String chunkId) {
        String sql = "SELECT hopperid FROM hopper_limit_hopper WHERE playerid = ? AND chunkid = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, playerId);
            pstmt.setString(2, chunkId);
            ResultSet rs = pstmt.executeQuery();
            int count = 0;
            while (rs.next()) {
                count++;
            }
            return count;
        } catch (Exception e) {
            throw new RuntimeException("error: " + e);
        }
    }

    @Override
    public int getHopperLimit() {
        String sql = "SELECT limitvalue FROM hopper_limit_table";
        try {
            ResultSet rs = conn.prepareStatement(sql).executeQuery();
            int count = 10;
            if (rs.next()) {
               count = rs.getInt("limitvalue");
            }
            return count;
        } catch (Exception e) {
            throw new RuntimeException("error: " + e);
        }
    }

    @Override
    public void setHopperLimit(int limit) {
        try {
            String sql = "UPDATE hopper_limit_table SET limitvalue = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, limit);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeHopper(HopperModel hopperModel) {
        try {
            String sql = "DELETE FROM hopper_limit_hopper WHERE hopperid = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, hopperModel.getId());
            pstmt.executeUpdate();
        } catch (Exception ex) {
            throw new RuntimeException("error: " + ex);
        }
    }

    @Override
    public void unload() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
