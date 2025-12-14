package com.ism.restaurant.model;

public class Livreur {
    private int id;
    private String nom;
    private String prenom;
    private String telephone;
    private boolean actif;

    public Livreur() {}

    public Livreur(String nom, String prenom, String telephone, boolean actif) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.actif = actif;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public boolean isActif() { return actif; }
    public void setActif(boolean actif) { this.actif = actif; }

    @Override
    public String toString() {
        return "Livreur [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", telephone=" + telephone + ", actif=" + actif + "]";
    }
}
