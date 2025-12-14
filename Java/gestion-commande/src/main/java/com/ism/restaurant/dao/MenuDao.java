package com.ism.restaurant.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ism.restaurant.config.DBConnection;
import com.ism.restaurant.model.Menu;

public class MenuDao {

    private final Connection connection;

    public MenuDao() {
        this.connection = DBConnection.getConnection();
    }

public long insertMenu(Menu menu) {

    String sql = """
        INSERT INTO menu (nom, image_url, burger_id, prix, is_archive)
        VALUES (?, ?, ?, ?, false)
    """;

    try (PreparedStatement ps = connection.prepareStatement(
                sql, Statement.RETURN_GENERATED_KEYS))  {

        ps.setString(1, menu.getNom());
        ps.setString(2, menu.getImageUrl());
        ps.setInt(3, menu.getBurger().getId());
        ps.setDouble(4, menu.getPrix());

        ps.executeUpdate();

        try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getLong(1);
                }
            }
            throw new SQLException("ID non généré");

    }   catch (SQLException e) {
        throw new RuntimeException("Erreur lors de l’ajout du menu", e);
    }
}
}

