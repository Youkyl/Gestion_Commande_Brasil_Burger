package com.ism.restaurant.model;

public class Quartier {
    private int id;
    private String nom;

    public Quartier() {}

    public Quartier(String nom) { this.nom = nom; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    @Override
    public String toString() { return "Quartier [id=" + id + ", nom=" + nom + "]"; }
}
