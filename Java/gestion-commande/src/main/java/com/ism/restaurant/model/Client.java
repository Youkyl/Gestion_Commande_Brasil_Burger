package com.ism.restaurant.model;

import java.time.LocalDateTime;

public class Client {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String adresse;
    private LocalDateTime dateCreation;
    private boolean actif;

    public Client() {}

    public Client(String nom, String prenom, String email, String password, String adresse, LocalDateTime dateCreation, boolean actif) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.adresse = adresse;
        this.dateCreation = dateCreation;
        this.actif = actif;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }
    public boolean isActif() { return actif; }
    public void setActif(boolean actif) { this.actif = actif; }

    @Override
    public String toString() {
        return "Client [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", adresse=" + adresse + ", dateCreation=" + dateCreation + ", actif=" + actif + "]";
    }
}
