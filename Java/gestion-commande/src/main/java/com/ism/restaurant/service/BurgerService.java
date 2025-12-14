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


    public List<Burger> searchBurgerByName(String nom) {

        if (nom == null || nom.isBlank()) {
            throw new IllegalArgumentException("Le nom du burger est obligatoire");
        }

        return burgerDao.findByNom(nom);
}


    public void modifierBurgerPartiel(int id, String nom, String ingredient, Double prix, String imageUrl) {

    Burger burger = burgerDao.findById(id);

    if (burger == null) {
        throw new RuntimeException("Burger introuvable");
    }

    if (nom != null && !nom.isBlank()) {
        burger.setNom(nom);
    }
    if (ingredient != null && !ingredient.isBlank()) {
        burger.setingredient(ingredient);
    }
    if (prix != null && prix > 0) {
        burger.setPrix(prix);
    }
    if (imageUrl != null && !imageUrl.isBlank()) {
        burger.setImageUrl(imageUrl);
    }

    burgerDao.updateBurger(burger);
}
    

    public void archiverBurger(int id) {

        if (id <= 0) {
            throw new IllegalArgumentException("ID du burger invalide");
        }

        burgerDao.archiverBurger(id);
    }



}
