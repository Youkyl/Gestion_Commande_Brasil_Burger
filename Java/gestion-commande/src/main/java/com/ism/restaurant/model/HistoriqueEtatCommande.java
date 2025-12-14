package com.ism.restaurant.model;

import java.time.LocalDateTime;

public class HistoriqueEtatCommande {
    private int id;
    private EtatCommande ancienEtat;
    private EtatCommande nouvelEtat;
    private LocalDateTime dateChangement;

    public HistoriqueEtatCommande() {}

    public HistoriqueEtatCommande(EtatCommande ancienEtat, EtatCommande nouvelEtat, LocalDateTime dateChangement) {
        this.ancienEtat = ancienEtat;
        this.nouvelEtat = nouvelEtat;
        this.dateChangement = dateChangement;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public EtatCommande getAncienEtat() { return ancienEtat; }
    public void setAncienEtat(EtatCommande ancienEtat) { this.ancienEtat = ancienEtat; }
    public EtatCommande getNouvelEtat() { return nouvelEtat; }
    public void setNouvelEtat(EtatCommande nouvelEtat) { this.nouvelEtat = nouvelEtat; }
    public LocalDateTime getDateChangement() { return dateChangement; }
    public void setDateChangement(LocalDateTime dateChangement) { this.dateChangement = dateChangement; }

    @Override
    public String toString() {
        return "HistoriqueEtatCommande [id=" + id + ", ancienEtat=" + ancienEtat + ", nouvelEtat=" + nouvelEtat + ", dateChangement=" + dateChangement + "]";
    }
}
