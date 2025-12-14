package com.ism.restaurant.view;

import java.util.List;
import java.util.Scanner;

import com.ism.restaurant.model.Burger;
import com.ism.restaurant.service.BurgerService;
import com.ism.restaurant.service.MenuService;

public class MenuView {
    
    private final MenuService menuService = new MenuService();
    private final BurgerService burgerService = new BurgerService();
    private final Scanner scanner = new Scanner(System.in);


    public void ajouterMenu() {

        System.out.print("Nom du menu : ");
        String nom = scanner.nextLine();

        System.out.print("Image URL : ");
        String imageUrl = scanner.nextLine();

        System.out.print("Nom du burger à rechercher : ");
        String nomBurger = scanner.nextLine();

        List<Burger> burgers = burgerService.searchBurgerByName(nomBurger);

        if (burgers.isEmpty()) {
            System.out.println("Aucun burger trouvé.");
            return;
        }

        burgers.forEach(b ->
            System.out.println("ID: " + b.getId() + " | " + b.getNom())
        );

        System.out.print("ID du burger : ");
        int burgerId = scanner.nextInt();
        scanner.nextLine();

        menuService.ajouterMenu(nom, imageUrl, burgerId);

        System.out.println("Menu ajouté avec succès (prix calculé automatiquement).");
}

}
