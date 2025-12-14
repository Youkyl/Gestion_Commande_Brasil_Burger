package com.ism.restaurant.service;

import com.ism.restaurant.dao.ComplementDao;
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

}
