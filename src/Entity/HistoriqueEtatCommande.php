<?php

namespace App\Entity;

use App\Repository\HistoriqueEtatCommandeRepository;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: HistoriqueEtatCommandeRepository::class)]
#[ORM\Table(name: '`historique_etat_commande`')]
class HistoriqueEtatCommande
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(length: 30, nullable: true)]
    private ?string $ancien_etat = null;

    #[ORM\Column(length: 30)]
    private ?string $nouvel_etat = null;

    #[ORM\Column]
    private ?\DateTime $date_changement = null;

    #[ORM\ManyToOne(inversedBy: 'historiqueEtatCommandes')]
    private ?Commande $commande = null;

    #[ORM\ManyToOne(inversedBy: 'historiqueEtatCommandes')]
    private ?User $gestionnaire = null;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getAncienEtat(): ?string
    {
        return $this->ancien_etat;
    }

    public function setAncienEtat(?string $ancien_etat): static
    {
        $this->ancien_etat = $ancien_etat;

        return $this;
    }

    public function getNouvelEtat(): ?string
    {
        return $this->nouvel_etat;
    }

    public function setNouvelEtat(string $nouvel_etat): static
    {
        $this->nouvel_etat = $nouvel_etat;

        return $this;
    }

    public function getDateChangement(): ?\DateTime
    {
        return $this->date_changement;
    }

    public function setDateChangement(\DateTime $date_changement): static
    {
        $this->date_changement = $date_changement;

        return $this;
    }

    public function getCommande(): ?Commande
    {
        return $this->commande;
    }

    public function setCommande(?Commande $commande): static
    {
        $this->commande = $commande;

        return $this;
    }

    public function getGestionnaire(): ?User
    {
        return $this->gestionnaire;
    }

    public function setGestionnaire(?User $gestionnaire): static
    {
        $this->gestionnaire = $gestionnaire;

        return $this;
    }
}
