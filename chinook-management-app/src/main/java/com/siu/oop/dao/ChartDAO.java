package com.siu.oop.dao;

import com.siu.oop.model.AlbumChartDTO;
import com.siu.oop.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChartDAO {
    public List<AlbumChartDTO> getTopArtistsByAlbums() {
        List<AlbumChartDTO> list = new ArrayList<>();
        
        // SỬA LẠI ĐIỀU KIỆN JOIN: al.ArtistId
        String sql = "SELECT a.Name, COUNT(al.AlbumId) as TotalAlbums " +
                     "FROM Artist a JOIN Album al ON a.ArtistId = al.ArtistId " +
                     "GROUP BY a.ArtistId, a.Name " + // Thêm a.Name để tránh lỗi nhóm
                     "ORDER BY TotalAlbums DESC LIMIT 30";
                     
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
             
            while (rs.next()) {
                list.add(new AlbumChartDTO(rs.getString("Name"), rs.getInt("TotalAlbums")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}