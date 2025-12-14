package com.ism.restaurant.view;

import java.util.List;
import java.util.Scanner;

import com.ism.restaurant.model.Burger;
import com.ism.restaurant.model.Menu;
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
        

         try {
            long id =  menuService.ajouterMenu(nom, imageUrl, burgerId);

            System.out.println("Menu ajouté avec succès (ID = " + id + " prix calculé automatiquement");
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }


}

    public void modifierMenu() {

        System.out.print("Nom du Menu à rechercher : ");
        String nom = scanner.nextLine();

        List<Menu> menus = menuService.searchMenuByName(nom);

        if (menus.isEmpty()) {
            System.out.println("Aucun menu trouvé.");
            return;
        }

        menus.forEach(m ->
            System.out.println("ID: " + m.getId() + " | " + m.getNom())
        );

        System.out.print("ID du menu à modifier : ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nouveau nom (laisser vide pour ne pas changer) : ");
        String newNom = scanner.nextLine();

        System.out.print("Nouvelle image URL (laisser vide) : ");
        String imageUrl = scanner.nextLine();

        Integer burgerId = null;

        System.out.print("Voulez-vous changer le burger ? (o/n) : ");
        String changerBurger = scanner.nextLine();

        if ("o".equalsIgnoreCase(changerBurger)) {

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


            System.out.print("ID du nouveau burger : ");
            burgerId = scanner.nextInt();
            scanner.nextLine();
        }

        menuService.modifierMenuPartiel(id, newNom, imageUrl, burgerId);


        System.out.println("Menu modifié avec succès.");
}


public void archiverMenu() {

        System.out.print("Nom du Menu à rechercher : ");
        String nom = scanner.nextLine();

        List<Menu> menus = menuService.searchMenuByName(nom);

        if (menus.isEmpty()) {
            System.out.println("Aucun menu trouvé.");
            return;
        }

        menus.forEach(m ->
            System.out.println("ID: " + m.getId() + " | " + m.getNom())
        );

    System.out.print("ID du menu à archiver : ");
    int id = scanner.nextInt();
    scanner.nextLine();

    menuService.archiverMenu(id);

    System.out.println("Burger archivé avec succès.");
}


}
