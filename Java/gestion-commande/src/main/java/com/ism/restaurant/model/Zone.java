package com.ism.restaurant.model;

public class Zone {
    private int id;
    private String nom;
    private double prixLivraison;

    public Zone() {}

    public Zone(String nom, double prixLivraison) {
        this.nom = nom;
        this.prixLivraison = prixLivraison;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public double getPrixLivraison() { return prixLivraison; }
    public void setPrixLivraison(double prixLivraison) { this.prixLivraison = prixLivraison; }

    @Override
    public String toString() {
        return "Zone [id=" + id + ", nom=" + nom + ", prixLivraison=" + prixLivraison + "]";
    }
}
