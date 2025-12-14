package com.ism.restaurant.model;

import java.time.LocalDateTime;

public class Menu {

    private int id;
    private String nom;
    private String imageUrl;
    private double prixMenu;
    private boolean archive;
    private LocalDateTime dateCreation;

    private Burger burger;

    public Menu() {}

    public Menu(String nom, double prixMenu, String imageUrl, boolean archive) {
        this.nom = nom;
        this.prixMenu = prixMenu;
        this.imageUrl = imageUrl;
        this.archive = archive;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public double getPrix() { return prixMenu; }
    public void setPrix(double prixMenu) { this.prixMenu = prixMenu; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public boolean isArchive() { return archive; }
    public void setArchive(boolean archive) { this.archive = archive; }
    public Burger getBurger() { return burger; }
    public void setBurger(Burger burger) { this.burger = burger; }

    @Override
    public String toString() {
        return "Menu [id=" + id + ", nom=" + nom + ", imageUrl=" + imageUrl + ", prix=" + prixMenu + ", archive=" + archive
                + ", dateCreation=" + dateCreation + ", burger=" + burger + "]";
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }
}
