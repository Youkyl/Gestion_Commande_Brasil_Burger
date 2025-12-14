package com.ism.restaurant.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ism.restaurant.config.DBConnection;
import com.ism.restaurant.model.Burger;
import com.ism.restaurant.model.Complement;
import com.ism.restaurant.model.TypeComplement;

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

    public void updateComplement(Complement complement) {

        String sql = """
            UPDATE complement
            SET nom = ?,
                prix = ?,
                image_url = ?
            WHERE id = ? AND is_archive = false
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, complement.getNom());
            ps.setDouble(2, complement.getPrix());
            ps.setString(3, complement.getImageUrl());
            ps.setInt(4, complement.getId());

            int rows = ps.executeUpdate();

            if (rows == 0) {
                throw new RuntimeException("Complément introuvable ou archivé");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la modification du complément", e);
        }
    }



    public List<Complement> findByNom(String nom) {

        String sql = """
            SELECT id, nom, type, prix, stock, image_url, is_archive
            FROM complement
            WHERE nom ILIKE ? AND is_archive = false
        """;

        List<Complement> complements = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, "%" + nom + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Complement complement = new Complement();

                complement.setId(rs.getInt("id"));
                complement.setNom(rs.getString("nom"));
                complement.setType(TypeComplement.valueOf(rs.getString("type")));
                complement.setPrix(rs.getDouble("prix"));
                complement.setStock(rs.getInt("stock"));
                complement.setImageUrl(rs.getString("image_url"));
                complement.setArchive(rs.getBoolean("is_archive"));

                complements.add(complement);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur recherche burger par nom", e);
        }

        return complements;
    }


    public List<Complement> findByType(TypeComplement type) {

        String sql = """
            SELECT id, nom, type, prix, stock, image_url, is_archive
            FROM complement
            WHERE type = ?::type_complement
            AND is_archive = false
        """;

        List<Complement> complements = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, type.name());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Complement complement = new Complement();
                complement.setId(rs.getInt("id"));
                complement.setNom(rs.getString("nom"));
                complement.setType(TypeComplement.valueOf(rs.getString("type")));
                complement.setPrix(rs.getDouble("prix"));
                complement.setStock(rs.getInt("stock"));
                complement.setImageUrl(rs.getString("image_url"));
                complement.setArchive(rs.getBoolean("is_archive"));

                complements.add(complement);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur recherche complément par type", e);
        }

        return complements;
    }



    public Complement findById(int id) {

        String sql = """
            SELECT id, nom, type, prix, stock, image_url, is_archive
            FROM complement
            WHERE id = ? AND is_archive = false
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

             ps.setInt(1, id); // 👈 IMPORTANT

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Complement complement = new Complement();

                complement.setId(rs.getInt("id"));
                complement.setNom(rs.getString("nom"));
                complement.setType(TypeComplement.valueOf(rs.getString("type")));
                complement.setPrix(rs.getDouble("prix"));
                complement.setStock(rs.getInt("stock"));
                complement.setImageUrl(rs.getString("image_url"));
                complement.setArchive(rs.getBoolean("is_archive"));

                return complement;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur recherche burger par type", e);
        }

        return null;
    }


}