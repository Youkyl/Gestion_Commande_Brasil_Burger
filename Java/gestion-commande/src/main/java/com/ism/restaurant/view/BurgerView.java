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


    public void modifierBurger() {

        System.out.print("Nom du burger à rechercher : ");
        String nom = scanner.nextLine();

        List<Burger> burgers = service.searchBurgerByName(nom);

        if (burgers.isEmpty()) {
            System.out.println("Aucun burger trouvé.");
            return;
        }

        burgers.forEach(b ->
            System.out.println("ID: " + b.getId() + " | " + b.getNom())
        );

        System.out.print("ID du burger à modifier : ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nouveau nom (laisser vide pour ne pas changer) : ");
        String newNom = scanner.nextLine();

        System.out.print("Nouvelle description (laisser vide) : ");
        String description = scanner.nextLine();

        System.out.print("Nouveau prix (0 pour ne pas changer) : ");
        double prixInput = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Nouvelle image URL (laisser vide) : ");
        String imageUrl = scanner.nextLine();

        Double prix = prixInput > 0 ? prixInput : null;

        service.modifierBurgerPartiel(id, newNom, description, prix, imageUrl);

        System.out.println("Burger modifié avec succès.");
}

    public void archiverBurger() {

        System.out.print("Nom du burger à rechercher : ");
        String nom = scanner.nextLine();

        List<Burger> burgers = service.searchBurgerByName(nom);

        if (burgers.isEmpty()) {
            System.out.println("Aucun burger trouvé.");
            return;
        }

        burgers.forEach(b ->
            System.out.println("ID: " + b.getId() + " | " + b.getNom())
        );

        System.out.print("ID du burger à archiver : ");
        int id = scanner.nextInt();
        scanner.nextLine();

        service.archiverBurger(id);

        System.out.println("Burger archivé avec succès.");
    }



    public void menuBurger() {

        while (true) {
            System.out.println("\n=== GESTION DES BURGERS ===");
            System.out.println("1. Ajouter un burger");
            System.out.println("2. Modifier un burger");
            System.out.println("3. Archiver un burger");
            System.out.println("0. Retour");
            System.out.print("Choix : ");

            String choix = scanner.nextLine();

            switch (choix) {
                case "1" -> ajouterBurger();
                case "2" -> modifierBurger();
                case "3" -> archiverBurger();
                case "0" -> {
                    return;
                }
                default -> System.out.println("Choix invalide.");
            }
        }
    }



}
