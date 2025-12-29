<?php

namespace App\Entity;

use App\Repository\CommandeRepository;
use App\Entity\Client;
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
}
