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

    public void updateBurger(Burger burger) {
        String sql = """
            UPDATE burger
            SET nom = ?, ingredient = ?, prix = ?, image_url = ?
            WHERE id = ? AND is_archive = false
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, burger.getNom());
            ps.setString(2, burger.getingredient());
            ps.setDouble(3, burger.getPrix());
            ps.setString(4, burger.getImageUrl());
            ps.setInt(5, burger.getId());

            int rows = ps.executeUpdate();

            if (rows == 0) {
                throw new RuntimeException("Burger introuvable ou archivé");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la modification du burger", e);
        }
}

public List<Burger> findByNom(String nom) {

    String sql = """
        SELECT id, nom, ingredient, prix, image_url, is_archive
        FROM burger
        WHERE nom ILIKE ? AND is_archive = false
    """;

    List<Burger> burgers = new ArrayList<>();

    try (PreparedStatement ps = connection.prepareStatement(sql)) {

        ps.setString(1, "%" + nom + "%");

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Burger burger = new Burger();
            burger.setId(rs.getInt("id"));
            burger.setNom(rs.getString("nom"));
            burger.setingredient(rs.getString("ingredient"));
            burger.setPrix(rs.getDouble("prix"));
            burger.setImageUrl(rs.getString("image_url"));
            burger.setArchive(rs.getBoolean("is_archive"));

            burgers.add(burger);
        }

    } catch (SQLException e) {
        throw new RuntimeException("Erreur recherche burger par nom", e);
    }

    return burgers;
}

public Burger findById(int id) {

    String sql = """
        SELECT id, nom, ingredient, prix, image_url, is_archive
        FROM burger
        WHERE id = ? AND is_archive = false
    """;

    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Burger burger = new Burger();
            burger.setId(rs.getInt("id"));
            burger.setNom(rs.getString("nom"));
            burger.setingredient(rs.getString("ingredient"));
            burger.setPrix(rs.getDouble("prix"));
            burger.setImageUrl(rs.getString("image_url"));
            return burger;
        }
    } catch (SQLException e) {
        throw new RuntimeException("Burger introuvable", e);
    }

    return null;
}

public void archiverBurger(int id) {

    String sql = """
        UPDATE burger
        SET is_archive = true
        WHERE id = ? AND is_archive = false
    """;

    try (PreparedStatement ps = connection.prepareStatement(sql)) {

        ps.setInt(1, id);
        int rows = ps.executeUpdate();

        if (rows == 0) {
            throw new RuntimeException("Burger introuvable ou déjà archivé");
        }

    } catch (SQLException e) {
        throw new RuntimeException("Erreur lors de l’archivage du burger", e);
    }
}
    

}
