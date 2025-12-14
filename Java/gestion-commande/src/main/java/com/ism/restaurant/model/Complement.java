package com.ism.restaurant.model;

public class Complement {
    private int id;
    private String nom;
    private double prix;
    private String imageUrl;
    private boolean archive;

    public Complement() {}

    public Complement(String nom, double prix, String imageUrl, boolean archive) {
        this.nom = nom;
        this.prix = prix;
        this.imageUrl = imageUrl;
        this.archive = archive;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public boolean isArchive() { return archive; }
    public void setArchive(boolean archive) { this.archive = archive; }

    @Override
    public String toString() {
        return "Complement [id=" + id + ", nom=" + nom + ", prix=" + prix + ", imageUrl=" + imageUrl + ", archive=" + archive + "]";
    }
}
