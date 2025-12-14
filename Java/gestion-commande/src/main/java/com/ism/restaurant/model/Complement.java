package com.ism.restaurant.model;

public class Complement {

    private int id;
    private String nom;
    private TypeComplement type; // FRITE | BOISSON
    private double prix;
    private int stock;
    private String imageUrl;
    private boolean archive;

    public Complement() {}

    public Complement(int id, String nom, TypeComplement type, double prix, int stock, String imageUrl, boolean archive) {
        this.id = id;
        this.nom = nom;
        this.type = type;
        this.prix = prix;
        this.stock = stock;
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
    public TypeComplement getType() { return type; }
    public void setType(TypeComplement type) { this.type = type; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    @Override
    public String toString() {
        return "Complement{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", type=" + type +
                ", prix=" + prix +
                ", stock=" + stock +
                ", imageUrl='" + imageUrl + '\'' +
                ", archive=" + archive +
                '}';
    }
}