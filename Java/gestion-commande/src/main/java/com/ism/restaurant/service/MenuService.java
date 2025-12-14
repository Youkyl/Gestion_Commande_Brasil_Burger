package com.ism.restaurant.service;

import com.ism.restaurant.dao.BurgerDao;
import com.ism.restaurant.dao.MenuDao;
import com.ism.restaurant.model.Burger;
import com.ism.restaurant.model.Menu;

public class MenuService {
    

    private final MenuDao menuDao;

    private final BurgerDao burgerDao;
    
    public MenuService() {
        this.menuDao = new MenuDao();
        this.burgerDao = new BurgerDao();

    }


    public void ajouterMenu(String nom, String imageUrl, int burgerId) {

        if (nom == null || nom.isBlank()) {
            throw new IllegalArgumentException("Nom du menu obligatoire");
        }

        Burger burger = burgerDao.findById(burgerId);

        if (burger == null || burger.isArchive()) {
            throw new RuntimeException("Burger invalide ou archivé");
        }

        double prixMenu = burger.getPrix() + 1500;

        Menu menu = new Menu();
        menu.setNom(nom);
        menu.setImageUrl(imageUrl);
        menu.setBurger(burger);
        menu.setPrix(prixMenu);

        menuDao.insertMenu(menu);
}


}
