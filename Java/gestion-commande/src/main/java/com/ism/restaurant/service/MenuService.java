package com.ism.restaurant.service;

import java.util.List;

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

    public List<Menu> searchMenuByName(String nom) {

            if (nom == null || nom.isBlank()) {
                throw new IllegalArgumentException("Le nom du menu est obligatoire");
            }

            return menuDao.findByNom(nom);
    }



    public void modifierMenuPartiel(
        int menuId,
        String nom,
        String imageUrl,
        Integer burgerId
) {

    Menu menu = menuDao.findById(menuId);

    if (menu == null || menu.isArchive()) {
        throw new RuntimeException("Menu introuvable ou archivé");
    }

    // 🔒 On garde TOUJOURS une référence valide au burger
    Burger burgerActuel = menu.getBurger();

    if (nom != null && !nom.isBlank()) {
        menu.setNom(nom);
    }

    if (imageUrl != null && !imageUrl.isBlank()) {
        menu.setImageUrl(imageUrl);
    }

    // 🔁 Si on veut changer le burger
    if (burgerId != null) {

        Burger nouveauBurger = burgerDao.findById(burgerId);

        if (nouveauBurger == null || nouveauBurger.isArchive()) {
            throw new RuntimeException("Burger invalide ou archivé");
        }

        menu.setBurger(nouveauBurger);
        menu.setPrix(nouveauBurger.getPrix() + 1500);

    } else {
        // 🔐 IMPORTANT : on conserve le burger existant
        menu.setBurger(burgerActuel);
    }

    menuDao.updateMenu(menu);
}

    public void archiverMenu(int id) {

        if (id <= 0) {
            throw new IllegalArgumentException("ID du menu invalide");
        }

        menuDao.archiverMenu(id);
}

}
