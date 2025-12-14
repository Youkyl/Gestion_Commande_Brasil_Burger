
package com.ism.restaurant.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;


public class DBConnection {

    private static Connection connection;
    

    private DBConnection() {}

    public static Connection getConnection() {
        if (connection == null) {
            try {
                String url = System.getenv("DB_URL");
                String user = System.getenv("DB_USER");
                String password = System.getenv("DB_PASSWORD");

                if (url == null || user == null || password == null) {
                    throw new RuntimeException("Variables d'environnement DB manquantes");
                }

                connection = DriverManager.getConnection(url, user, password);

            } catch (SQLException e) {
                throw new RuntimeException("Erreur connexion base de données", e);
            }
        }
        return connection;
    }
}