package com.ism.restaurant.view;

import java.util.List;
import java.util.Scanner;

import com.ism.restaurant.model.Burger;
import com.ism.restaurant.service.BurgerService;

public class BurgerView {

    private final BurgerService service = new BurgerService();
    private final Scanner scanner = new Scanner(System.in);

    public void ajouterBurger() {
        System.out.println("=== AJOUT D'UN BURGER ===");

        System.out.print("Nom : ");
        String nom = scanner.nextLine();

        System.out.print("Description / Ingrédients : ");
        String description = scanner.nextLine();

        System.out.print("Prix : ");
        double prix = Double.parseDouble(scanner.nextLine());

        System.out.print("Image URL : ");
        String imageUrl = scanner.nextLine();

        try {
            long id = service.ajouterBurger(nom, description, prix, imageUrl);
            System.out.println("Burger ajouté avec succès (ID = " + id + ")");
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }


   

}
