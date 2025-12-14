package com.ism.restaurant.view;

import java.util.Scanner;

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
    
}
