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

        List<Burger> burgers = service.searchBurgerByNaom(nom);

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


}
