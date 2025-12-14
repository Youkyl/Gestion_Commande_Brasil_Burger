package com.ism.restaurant.view;

import java.util.List;
import java.util.Scanner;

import com.ism.restaurant.model.Burger;
import com.ism.restaurant.model.Complement;
import com.ism.restaurant.model.TypeComplement;
import com.ism.restaurant.service.ComplementService;

public class ComplementView {
    
    private final ComplementService complementService = new ComplementService();
    private final Scanner scanner = new Scanner(System.in);


    
    public void ajouterComplement() {

        System.out.println("=== AJOUT D'UN COMPLEMENT ===");

        System.out.print("Nom : ");
        String nom = scanner.nextLine();

        System.out.print("Type (1=FRITE, 2=BOISSON) : ");
        int typeInput = Integer.parseInt(scanner.nextLine());
        var type = switch (typeInput) {
            case 1 -> TypeComplement.FRITE;
            case 2 -> TypeComplement.BOISSON;
            default -> throw new IllegalArgumentException("Type invalide");
        };

        System.out.print("Prix : ");
        double prix = Double.parseDouble(scanner.nextLine());

        System.out.print("Stock : ");
        int stock = Integer.parseInt(scanner.nextLine());

        System.out.print("Image URL : ");
        String imageUrl = scanner.nextLine();

        try {
            long id = complementService.ajouterComplement(nom, type, prix, stock, imageUrl
) ;
            System.out.println("Complément ajouté avec succès (ID = " + id + ")");
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }


    public void modifierComplement() {

        System.out.print("Type du complément à rechercher : ");
        TypeComplement typeInput = TypeComplement.valueOf(scanner.nextLine().toUpperCase());

        List<Complement> complements = complementService.searchComplementByType(typeInput);

        if (complements.isEmpty()) {
            System.out.println("Aucun complément trouvé.");
            return;
        }

        complements.forEach(c ->
            System.out.println("ID: " + c.getId() + " | " + c.getNom())
        );


        System.out.print("ID du complément à modifier : ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nouveau nom (laisser vide pour ne pas changer) : ");
        String newNom = scanner.nextLine();

        System.out.print("Nouveau prix (0 pour ne pas changer) : ");
        double prixInput = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Nouvelle image URL (laisser vide) : ");
        String imageUrl = scanner.nextLine();

        Double prix = prixInput > 0 ? prixInput : null;

        complementService.modifierComplementPartiel(id, newNom, null, prix, null, imageUrl);

        System.out.println("Complément modifié avec succès.");
}

    public void archiverComplement() {

        System.out.print("type du complément à rechercher : ");
        TypeComplement typeInput = TypeComplement.valueOf(scanner.nextLine().toUpperCase());

        List<Complement> complements = complementService.searchComplementByType(typeInput);

        if (complements.isEmpty()) {
            System.out.println("Aucun complément trouvé.");
            return;
        }

        complements.forEach(c ->
            System.out.println("ID: " + c.getId() + " | " + c.getNom())
        );

        System.out.print("ID du complément à archiver : ");
        int id = scanner.nextInt();
        scanner.nextLine();

        complementService.archiverComplement(id);
        System.out.println("Complément archivé avec succès.");
    }
    



    public void menuComplement() {

        while (true) {
            System.out.println("\n=== GESTION DES COMPLEMENTS ===");
            System.out.println("1. Ajouter un complément");
            System.out.println("2. Modifier un complément");
            System.out.println("3. Archiver un complément");
            System.out.println("0. Retour au menu principal");
            System.out.print("Choix : ");

            String choix = scanner.nextLine();

            switch (choix) {
                case "1" -> ajouterComplement();
                case "2" -> modifierComplement();
                case "3" -> archiverComplement();
                case "0" -> {
                    return;
                }
                default -> System.out.println("Choix invalide.");
            }
        }
    }


}
