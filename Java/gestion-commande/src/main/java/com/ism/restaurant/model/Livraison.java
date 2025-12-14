package com.ism.restaurant.model;

import java.time.LocalDateTime;

public class Livraison {
    private int id;
    private LocalDateTime dateAffectation;
    private StatutLivraison statut;
    private Livreur livreur;

    public Livraison() {}

    public Livraison(LocalDateTime dateAffectation, StatutLivraison statut, Livreur livreur) {
        this.dateAffectation = dateAffectation;
        this.statut = statut;
        this.livreur = livreur;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public LocalDateTime getDateAffectation() { return dateAffectation; }
    public void setDateAffectation(LocalDateTime dateAffectation) { this.dateAffectation = dateAffectation; }
    public StatutLivraison getStatut() { return statut; }
    public void setStatut(StatutLivraison statut) { this.statut = statut; }
    public Livreur getLivreur() { return livreur; }
    public void setLivreur(Livreur livreur) { this.livreur = livreur; }

    @Override
    public String toString() {
        return "Livraison [id=" + id + ", dateAffectation=" + dateAffectation + ", statut=" + statut + ", livreur=" + livreur + "]";
    }
}
