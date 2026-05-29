/*
 * Food Nutrition Management System
 *
 * Developed by:
 * Truong Gia Huy
Student ID: 87482503626
 * Nguyen Ngoc Nhu Y
 *Student ID: 87482503633
 * Course: Object Oriented Programming
 */
package com.siu.oop.dao;

import com.siu.oop.model.Artist;
import com.siu.oop.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArtistDAO implements GenericDAO<Artist, Integer> {

    public List<Artist> getAllArtists() {
        return findAll();
    }

    @Override
    public Optional<Artist> findById(Integer id) {
        String sql = "SELECT * FROM Artist WHERE ArtistId = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, 
                     ResultSet.TYPE_SCROLL_INSENSITIVE, 
                     ResultSet.CONCUR_READ_ONLY)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Artist(rs.getInt("ArtistId"), rs.getString("Name")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Artist> findAll() {
        List<Artist> list = new ArrayList<>();
        String sql = "SELECT * FROM Artist ORDER BY ArtistId ASC";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, 
                     ResultSet.TYPE_SCROLL_INSENSITIVE, 
                     ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = ps.executeQuery()) {
             
            while (rs.next()) {
                list.add(new Artist(rs.getInt("ArtistId"), rs.getString("Name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Artist> searchByName(String keyword) {
        List<Artist> list = new ArrayList<>();
        String sql = "SELECT * FROM Artist WHERE Name LIKE ? ORDER BY ArtistId ASC";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, 
                     ResultSet.TYPE_SCROLL_INSENSITIVE, 
                     ResultSet.CONCUR_READ_ONLY)) {
                 
            ps.setString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Artist(rs.getInt("ArtistId"), rs.getString("Name")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean save(Artist artist) {
        String sql = "INSERT INTO Artist (Name) VALUES (?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, artist.name());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Artist artist) {
        String sql = "UPDATE Artist SET Name = ? WHERE ArtistId = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, artist.name());
            ps.setInt(2, artist.artistId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM Artist WHERE ArtistId = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}