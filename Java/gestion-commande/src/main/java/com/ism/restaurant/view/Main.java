package com.ism.restaurant.view;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            afficherMenuPrincipal();
            String choix = scanner.nextLine();

            switch (choix) {
                case "1" -> new BurgerView().menuBurger();
                case "2" -> new MenuView().menuMenu();
                case "3" -> new ComplementView().menuComplement();
                case "0" -> {
                    System.out.println("Au revoir 👋");
                    System.exit(0);
                }
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    private static void afficherMenuPrincipal() {
        System.out.println("\n===== BRASIL BURGER - GESTION =====");
        System.out.println("1. Gestion des Burgers");
        System.out.println("2. Gestion des Menus");
        System.out.println("3. Gestion des Compléments");
        System.out.println("0. Quitter");
        System.out.print("Votre choix : ");
    }
}
