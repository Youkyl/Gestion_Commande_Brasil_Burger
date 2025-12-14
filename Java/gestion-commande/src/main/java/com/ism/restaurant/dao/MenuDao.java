package com.ism.restaurant.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ism.restaurant.config.DBConnection;
import com.ism.restaurant.model.Burger;
import com.ism.restaurant.model.Menu;

public class MenuDao {

    private final Connection connection;
    private final BurgerDao burgerDao;

    public MenuDao() {
        this.connection = DBConnection.getConnection();
        this.burgerDao = new BurgerDao();
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

    public void updateMenu(Menu menu) {

        String sql = """
            UPDATE menu
            SET nom = ?, image_url = ?, burger_id = ?, prix = ?
            WHERE id = ? AND is_archive = false
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, menu.getNom());
            ps.setString(2, menu.getImageUrl());
            ps.setInt(3, menu.getBurger().getId());
            ps.setDouble(4, menu.getPrix());
            ps.setInt(5, menu.getId());

            int rows = ps.executeUpdate();

            if (rows == 0) {
                throw new RuntimeException("Menu introuvable ou archivé");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la modification du menu", e);
        }
    }


    public List<Menu> findByNom(String nom) {

        String sql = """
            SELECT id, nom, image_url, burger_id, prix, is_archive
            FROM menu
            WHERE nom ILIKE ? AND is_archive = false
        """;

        List<Menu> menus = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, "%" + nom + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Menu menu = new Menu();
                menu.setId(rs.getInt("id"));
                menu.setNom(rs.getString("nom"));
                menu.setImageUrl(rs.getString("image_url"));
                menu.setPrix(rs.getDouble("prix"));
                menu.setArchive(rs.getBoolean("is_archive"));

                menus.add(menu);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur recherche menu par nom", e);
        }

        return menus;
    }


    public Menu findById(int id) {

        String sql = """
            SELECT id, nom, image_url, burger_id, prix, is_archive
            FROM menu
            WHERE id = ? AND is_archive = false
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Menu menu = new Menu();
                menu.setId(rs.getInt("id"));
                menu.setNom(rs.getString("nom"));
                menu.setImageUrl(rs.getString("image_url"));
                menu.setPrix(rs.getDouble("prix"));
                menu.setArchive(rs.getBoolean("is_archive"));

                // 🔥 LIGNE CRITIQUE MANQUANTE
                int burgerId = rs.getInt("burger_id");
                Burger burger = burgerDao.findById(burgerId);
                menu.setBurger(burger);

                return menu;
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération du menu", e);
        }
}

public void archiverMenu(int id) {

    String sql = """
        UPDATE menu
        SET is_archive = true
        WHERE id = ? AND is_archive = false
    """;

    try (PreparedStatement ps = connection.prepareStatement(sql)) {

        ps.setInt(1, id);
        int rows = ps.executeUpdate();

        if (rows == 0) {
            throw new RuntimeException("Menu introuvable ou déjà archivé");
        }

    } catch (SQLException e) {
        throw new RuntimeException("Erreur lors de l’archivage du menu", e);
    }
}
    

}
