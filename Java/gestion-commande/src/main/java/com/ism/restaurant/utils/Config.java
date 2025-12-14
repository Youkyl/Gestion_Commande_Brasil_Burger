package com.ism.restaurant.utils;

import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static Properties props = new Properties();

    static {
        try {
            InputStream in = Config.class.getClassLoader().getResourceAsStream("application.properties");
            props.load(in);
        } catch(Exception e) {
            throw new RuntimeException("Erreur chargement config", e);
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}
