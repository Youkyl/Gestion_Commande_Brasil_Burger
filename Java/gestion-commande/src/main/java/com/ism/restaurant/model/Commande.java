package com.ism.restaurant.model;

import java.time.LocalDateTime;
import java.util.List;

public class Commande {
    private int id;
    private double prixTotal;
    private LocalDateTime dateCommande;
    private TypeCommande type;
    private EtatCommande etat;
    private Zone zone;
    private Client client;
    private List<LigneCommande> lignes;
    private List<CommandeComplement> complements;
    private Paiement paiement;
    private Livraison livraison;

    public Commande() {}

    public Commande(double prixTotal, LocalDateTime dateCommande, TypeCommande type, EtatCommande etat) {
        this.prixTotal = prixTotal;
        this.dateCommande = dateCommande;
        this.type = type;
        this.etat = etat;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public double getPrixTotal() { return prixTotal; }
    public void setPrixTotal(double prixTotal) { this.prixTotal = prixTotal; }
    public LocalDateTime getDateCommande() { return dateCommande; }
    public void setDateCommande(LocalDateTime dateCommande) { this.dateCommande = dateCommande; }
    public TypeCommande getType() { return type; }
    public void setType(TypeCommande type) { this.type = type; }
    public EtatCommande getEtat() { return etat; }
    public void setEtat(EtatCommande etat) { this.etat = etat; }
    public Zone getZone() { return zone; }
    public void setZone(Zone zone) { this.zone = zone; }
    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
    public List<LigneCommande> getLignes() { return lignes; }
    public void setLignes(List<LigneCommande> lignes) { this.lignes = lignes; }
    public List<CommandeComplement> getComplements() { return complements; }
    public void setComplements(List<CommandeComplement> complements) { this.complements = complements; }
    public Paiement getPaiement() { return paiement; }
    public void setPaiement(Paiement paiement) { this.paiement = paiement; }
    public Livraison getLivraison() { return livraison; }
    public void setLivraison(Livraison livraison) { this.livraison = livraison; }

    @Override
    public String toString() {
        return "Commande [id=" + id + ", prixTotal=" + prixTotal + ", dateCommande=" + dateCommande + ", type=" + type + ", etat=" + etat + "]";
    }
}
