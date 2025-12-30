<?php

namespace App\Entity;

use App\Repository\CommandeRepository;
use App\Entity\Client;
use App\Entity\CommandeDetail;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: CommandeRepository::class)]
#[ORM\Table(name: '`commande`')]
class Commande
{
    
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(length: 50)]
    private ?string $reference = null;

    #[ORM\Column(name: '`date_commande`')]
    private ?\DateTime $date_commande = null;

    #[ORM\Column(length: 50)]
    private ?string $type_commande = null;

    #[ORM\Column(length: 30)]
    private ?string $etat = null;

    #[ORM\Column(type: Types::DECIMAL, precision: 10, scale: 2)]
    private ?string $montant_total = null;

    #[ORM\ManyToOne (targetEntity: Client::class)]
    #[ORM\JoinColumn(name: "client_id", referencedColumnName: "id")]
    private ?Client $client;

    #[ORM\OneToOne(mappedBy: 'commande', targetEntity: Paiement::class)]
    private ?Paiement $paiement = null;

    /**
     * @var Collection<int, CommandeDetail>
     */
    #[ORM\OneToMany(targetEntity: CommandeDetail::class, mappedBy: 'commande')]
    private Collection $commandeDetails;

    /**
     * @var Collection<int, HistoriqueEtatCommande>
     */
    #[ORM\OneToMany(targetEntity: HistoriqueEtatCommande::class, mappedBy: 'commande')]
    private Collection $historiqueEtatCommandes;

    public function __construct()
    {
        $this->commandeDetails = new ArrayCollection();
        $this->historiqueEtatCommandes = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getReference(): ?string
    {
        return $this->reference;
    }

    public function setReference(string $reference): static
    {
        $this->reference = $reference;

        return $this;
    }

    public function getDateCommande(): ?\DateTime
    {
        return $this->date_commande;
    }

    public function setDateCommande(\DateTime $date_commande): static
    {
        $this->date_commande = $date_commande;

        return $this;
    }

    public function getTypeCommande(): ?string
    {
        return $this->type_commande;
    }

    public function setTypeCommande(string $type_commande): static
    {
        $this->type_commande = $type_commande;

        return $this;
    }

    public function getEtat(): ?string
    {
        return $this->etat;
    }

    public function setEtat(string $etat): static
    {
        $this->etat = $etat;

        return $this;
    }

    public function getMontantTotal(): ?string
    {
        return $this->montant_total;
    }

    public function setMontantTotal(string $montant_total): static
    {
        $this->montant_total = $montant_total;

        return $this;
    }

    public function getClient(): Client
    {
        return $this->client;
    }

    public function setClient(Client $client): static
    {
        $this->client = $client;

        return $this;
    }

    /**
     * @return Collection<int, CommandeDetail>
     */
    public function getCommandeDetails(): Collection
    {
        return $this->commandeDetails;
    }

    public function addCommandeDetail(CommandeDetail $commandeDetail): static
    {
        if (!$this->commandeDetails->contains($commandeDetail)) {
            $this->commandeDetails->add($commandeDetail);
            $commandeDetail->setCommande($this);
        }

        return $this;
    }

    public function removeCommandeDetail(CommandeDetail $commandeDetail): static
    {
        if ($this->commandeDetails->removeElement($commandeDetail)) {
            // set the owning side to null (unless already changed)
            if ($commandeDetail->getCommande() === $this) {
                $commandeDetail->setCommande(null);
            }
        }

        return $this;
    }


    public function getPaiement(): ?Paiement
    {
        return $this->paiement;
    }

    /**
     * @return Collection<int, HistoriqueEtatCommande>
     */
    public function getHistoriqueEtatCommandes(): Collection
    {
        return $this->historiqueEtatCommandes;
    }

    public function addHistoriqueEtatCommande(HistoriqueEtatCommande $historiqueEtatCommande): static
    {
        if (!$this->historiqueEtatCommandes->contains($historiqueEtatCommande)) {
            $this->historiqueEtatCommandes->add($historiqueEtatCommande);
            $historiqueEtatCommande->setCommande($this);
        }

        return $this;
    }

    public function removeHistoriqueEtatCommande(HistoriqueEtatCommande $historiqueEtatCommande): static
    {
        if ($this->historiqueEtatCommandes->removeElement($historiqueEtatCommande)) {
            // set the owning side to null (unless already changed)
            if ($historiqueEtatCommande->getCommande() === $this) {
                $historiqueEtatCommande->setCommande(null);
            }
        }

        return $this;
    }


}
