package com.ism.restaurant.dao;

import com.ism.restaurant.config.DBConnection;
import com.ism.restaurant.model.Burger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BurgerDao {

    private final Connection connection;

    public BurgerDao() {
        this.connection = DBConnection.getConnection();
    }

    // Ajouter un burger

    public long insertBurger(Burger burger) {
    String sql = """
        INSERT INTO burger (nom, ingredient, prix, image_url, is_archive)
        VALUES (?, ?, ?, ?, false)
    """;

    try (PreparedStatement ps = connection.prepareStatement(
            sql, Statement.RETURN_GENERATED_KEYS)) {

        ps.setString(1, burger.getNom());
        ps.setString(2, burger.getingredient ()); 
        ps.setDouble(3, burger.getPrix());
        ps.setString(4, burger.getImageUrl());
        ps.executeUpdate();

        try (ResultSet keys = ps.getGeneratedKeys()) {
            if (keys.next()) {
                return keys.getLong(1);
            }
        }
        throw new SQLException("ID non généré");
    } catch (SQLException e) {
        throw new RuntimeException("Erreur lors de l'ajout du burger", e);
    }
}

    


}
