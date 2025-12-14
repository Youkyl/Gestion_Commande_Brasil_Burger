package com.ism.restaurant.model;

import java.time.LocalDateTime;

public class Paiement {
    private int id;
    private LocalDateTime datePaiement;
    private double montant;
    private StatutPaiement statut;
    private ModePaiement mode;

    public Paiement() {}

    public Paiement(LocalDateTime datePaiement, double montant, StatutPaiement statut, ModePaiement mode) {
        this.datePaiement = datePaiement;
        this.montant = montant;
        this.statut = statut;
        this.mode = mode;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public LocalDateTime getDatePaiement() { return datePaiement; }
    public void setDatePaiement(LocalDateTime datePaiement) { this.datePaiement = datePaiement; }
    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }
    public StatutPaiement getStatut() { return statut; }
    public void setStatut(StatutPaiement statut) { this.statut = statut; }
    public ModePaiement getMode() { return mode; }
    public void setMode(ModePaiement mode) { this.mode = mode; }

    @Override
    public String toString() {
        return "Paiement [id=" + id + ", datePaiement=" + datePaiement + ", montant=" + montant + ", statut=" + statut + ", mode=" + mode + "]";
    }
}
