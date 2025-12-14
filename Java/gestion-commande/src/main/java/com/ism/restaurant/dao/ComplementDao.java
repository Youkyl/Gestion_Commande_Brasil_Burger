package com.ism.restaurant.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ism.restaurant.config.DBConnection;
import com.ism.restaurant.model.Complement;

public class ComplementDao {
    
      private final Connection connection;

    public ComplementDao() {
        this.connection = DBConnection.getConnection();
    }

    public long insertComplement(Complement complement) {

        String sql = """
            INSERT INTO complement (nom, type, prix, stock, image_url, is_archive)
                    VALUES (?, ?::type_complement, ?, ?, ?, false)

        """;

        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, complement.getNom());
            ps.setString(2, complement.getType().name());
            ps.setDouble(3, complement.getPrix());
            ps.setInt(4, complement.getStock());
            ps.setString(5, complement.getImageUrl());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getLong(1);
                }
        }
            throw new SQLException("ID non généré");

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout du complément", e);
    }
}
    
}
