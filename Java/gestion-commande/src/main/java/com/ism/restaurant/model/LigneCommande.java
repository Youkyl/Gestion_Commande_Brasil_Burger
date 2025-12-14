package com.ism.restaurant.model;

public class LigneCommande {
    private int id;
    private int quantite;
    private double prixUnitaire;
    private Burger burger;

    public LigneCommande() {}

    public LigneCommande(int quantite, double prixUnitaire, Burger burger) {
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
        this.burger = burger;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }
    public double getPrixUnitaire() { return prixUnitaire; }
    public void setPrixUnitaire(double prixUnitaire) { this.prixUnitaire = prixUnitaire; }
    public Burger getBurger() { return burger; }
    public void setBurger(Burger burger) { this.burger = burger; }

    @Override
    public String toString() {
        return "LigneCommande [id=" + id + ", quantite=" + quantite + ", prixUnitaire=" + prixUnitaire + ", burger=" + burger + "]";
    }
}
