<?php

namespace App\Entity;

use App\Repository\CommandeComplementRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: CommandeComplementRepository::class)]
#[ORM\Table(name: '`commande_complement`')]
class CommandeComplement
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column]
    private ?int $quantite = null;

    #[ORM\Column(type: Types::DECIMAL, precision: 10, scale: 2)]
    private ?string $prix_unitaire = null;

    #[ORM\ManyToOne(inversedBy: 'commandeComplements')]
    #[ORM\JoinColumn(name: "commande_detail_id", referencedColumnName: "id", nullable: false)]
    private ?CommandeDetail $commandeDetail = null;

    #[ORM\ManyToOne(inversedBy: 'commandeComplements')]
    #[ORM\JoinColumn(name: "complement_id", referencedColumnName: "id", nullable: false)]
    private ?Complement $complement = null;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getQuantite(): ?int
    {
        return $this->quantite;
    }

    public function setQuantite(int $quantite): static
    {
        $this->quantite = $quantite;

        return $this;
    }

    public function getPrixUnitaire(): ?string
    {
        return $this->prix_unitaire;
    }

    public function setPrixUnitaire(string $prix_unitaire): static
    {
        $this->prix_unitaire = $prix_unitaire;

        return $this;
    }

    public function getCommandeDetail(): ?CommandeDetail
    {
        return $this->commandeDetail;
    }

    public function setCommandeDetail(?CommandeDetail $commandeDetail): static
    {
        $this->commandeDetail = $commandeDetail;

        return $this;
    }

    public function getComplement(): ?Complement
    {
        return $this->complement;
    }

    public function setComplement(?Complement $complement): static
    {
        $this->complement = $complement;

        return $this;
    }
}
