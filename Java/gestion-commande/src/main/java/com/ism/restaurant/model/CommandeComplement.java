package com.ism.restaurant.model;

public class CommandeComplement {
    private int id;
    private int quantite;
    private double prixUnitaire;
    private Complement complement;

    public CommandeComplement() {}

    public CommandeComplement(int quantite, double prixUnitaire, Complement complement) {
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
        this.complement = complement;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }
    public double getPrixUnitaire() { return prixUnitaire; }
    public void setPrixUnitaire(double prixUnitaire) { this.prixUnitaire = prixUnitaire; }
    public Complement getComplement() { return complement; }
    public void setComplement(Complement complement) { this.complement = complement; }

    @Override
    public String toString() {
        return "CommandeComplement [id=" + id + ", quantite=" + quantite + ", prixUnitaire=" + prixUnitaire + ", complement=" + complement + "]";
    }
}
