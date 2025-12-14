package com.ism.restaurant.service;

import java.util.List;

import com.ism.restaurant.dao.ComplementDao;
import com.ism.restaurant.model.Burger;
import com.ism.restaurant.model.Complement;
import com.ism.restaurant.model.TypeComplement;

public class ComplementService {
    
    private final ComplementDao complementDao = new ComplementDao();

    public long ajouterComplement(
        String nom,
        TypeComplement type,
        double prix,
        int stock,
        String imageUrl
) {

        if (nom == null || nom.isBlank()) {
            throw new IllegalArgumentException("Nom obligatoire");
        }

        if (prix <= 0) {
            throw new IllegalArgumentException("Prix invalide");
        }

        if (stock < 0) {
            throw new IllegalArgumentException("Stock invalide");
        }

        if (type == null) {
            throw new IllegalArgumentException("Type de complément obligatoire");
        }


        Complement c = new Complement();
        c.setNom(nom);
        c.setType(type);
        c.setPrix(prix);
        c.setStock(stock);
        c.setImageUrl(imageUrl);

        return complementDao.insertComplement(c);
}


public List<Complement> searchComplementByType(TypeComplement type) {

        if (type == null) {
            throw new IllegalArgumentException("Le type du complément est obligatoire");
        }

        return complementDao.findByType(type);
    }

    


    public void modifierComplementPartiel(
            int id,
            String nom,
            TypeComplement type,
            Double prix,
            Integer stock,
            String imageUrl
    ) {

        Complement c = complementDao.findById(id);

        if (c == null || c.isArchive()) {
            throw new RuntimeException("Complément introuvable ou archivé");
        }

        if (nom != null && !nom.isBlank()) {
            c.setNom(nom);
        }

        if (type != null) {
            c.setType(type);
        }

        if (prix != null && prix > 0) {
            c.setPrix(prix);
        }

        if (stock != null && stock >= 0) {
            c.setStock(stock);
        }

        if (imageUrl != null && !imageUrl.isBlank()) {
            c.setImageUrl(imageUrl);
        }

        complementDao.updateComplement(c);
    }


    public List<Complement> searchComplementByName(String nom) {

        if (nom == null || nom.isBlank()) {
            throw new IllegalArgumentException("Le nom du complément est obligatoire");
        }

        return complementDao.findByNom(nom);
}
    


    }
