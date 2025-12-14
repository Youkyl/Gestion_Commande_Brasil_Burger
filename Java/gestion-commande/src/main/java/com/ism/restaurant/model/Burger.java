package com.ism.restaurant.model;

import java.time.LocalDateTime;
import java.util.List;

public class Burger {
    
    private int id;
    private String nom;
    private String ingredient ;
    private double prix;
    private String imageUrl;
    private LocalDateTime dateDeCreation;
    private boolean archive;
    private LigneCommande ligneCommande;
    private List<Menu> menus;

    public Burger(String nom, String ingredient , double prix, String imageUrl, LocalDateTime dateDeCreation,
            boolean archive, LigneCommande ligneCommande, List<Menu> menus) {
        this.nom = nom;
        this.ingredient  = ingredient ;
        this.prix = prix;
        this.imageUrl = imageUrl;
        this.dateDeCreation = dateDeCreation;
        this.archive = archive;
        this.ligneCommande = ligneCommande;
        this.menus = menus;
    }

    public Burger() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public String getingredient () {
        return ingredient ;
    }

    public double getPrix() {
        return prix;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public LocalDateTime getDateDeCreation() {
        return dateDeCreation;
    }

    public boolean isArchive() {
        return archive;
    }

    public LigneCommande getLigneCommande() {
        return ligneCommande;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setingredient (String ingredient ) {
        this.ingredient  = ingredient ;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    @Override
    public String toString() {
        return "Burger [id=" + id + ", nom=" + nom + ", ingredient =" + ingredient  + ", prix=" + prix + ", imageUrl="
                + imageUrl + ", dateDeCreation=" + dateDeCreation + ", archive=" + archive + ", ligneCommande="
                + ligneCommande + ", menus=" + menus + "]";
    }
}
