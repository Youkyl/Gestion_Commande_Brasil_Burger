package com.ism.restaurant.service;

import com.ism.restaurant.model.Burger;

import java.util.List;

import com.ism.restaurant.dao.BurgerDao;

public class BurgerService {

    private final BurgerDao burgerDao = new BurgerDao();

    public long ajouterBurger(String nom, String description, double prix, String imageUrl) {

        if (nom == null || nom.isBlank()) {
            throw new IllegalArgumentException("Le nom est obligatoire");
        }
        if (prix <= 0) {
            throw new IllegalArgumentException("Le prix doit être > 0");
        }
        if (imageUrl == null || imageUrl.isBlank()) {
            throw new IllegalArgumentException("L'URL de l'image est obligatoire");
        }

        Burger burger = new Burger();
        burger.setNom(nom);
        burger.setingredient(description);
        burger.setPrix(prix);
        burger.setImageUrl(imageUrl);

        return burgerDao.insertBurger(burger);
    }



    
}
