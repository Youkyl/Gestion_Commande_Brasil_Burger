<?php

namespace App\Entity;

use App\Repository\CommandeDetailRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: CommandeDetailRepository::class)]
#[ORM\Table(name: '`commande_detail`')]
class CommandeDetail
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column]
    private ?int $quantite = null;

    #[ORM\Column(type: Types::DECIMAL, precision: 10, scale: 2)]
    private ?string $prix_unitaire = null;

    #[ORM\ManyToOne(inversedBy: 'commandeDetails')]
    #[ORM\JoinColumn(name: "commande_id", referencedColumnName: "id", nullable: false)]
    private ?Commande $commande = null;

    #[ORM\ManyToOne(inversedBy: 'commandeDetails')]
    #[ORM\JoinColumn(name: "burger_id", referencedColumnName: "id", nullable: false)]
    private ?Burger $burger = null;

    #[ORM\ManyToOne(inversedBy: 'commandeDetails')]
    #[ORM\JoinColumn(name: "menu_id", referencedColumnName: "id", nullable: false)]     
    private ?Menu $menu = null;

    /**
     * @var Collection<int, CommandeComplement>
     */
    #[ORM\OneToMany(targetEntity: CommandeComplement::class, mappedBy: 'commandeDetail')]
    private Collection $commandeComplements;

    public function __construct()
    {
        $this->commandeComplements = new ArrayCollection();
    }

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

    public function getCommande(): ?Commande
    {
        return $this->commande;
    }

    public function setCommande(?Commande $commande): static
    {
        $this->commande = $commande;

        return $this;
    }

    public function getBurger(): ?Burger
    {
        return $this->burger;
    }

    public function setBurger(?Burger $burger): static
    {
        $this->burger = $burger;

        return $this;
    }

    public function getMenu(): ?Menu
    {
        return $this->menu;
    }

    public function setMenu(?Menu $menu): static
    {
        $this->menu = $menu;

        return $this;
    }

    /**
     * @return Collection<int, CommandeComplement>
     */
    public function getCommandeComplements(): Collection
    {
        return $this->commandeComplements;
    }

    public function addCommandeComplement(CommandeComplement $commandeComplement): static
    {
        if (!$this->commandeComplements->contains($commandeComplement)) {
            $this->commandeComplements->add($commandeComplement);
            $commandeComplement->setCommandeDetail($this);
        }

        return $this;
    }

    public function removeCommandeComplement(CommandeComplement $commandeComplement): static
    {
        if ($this->commandeComplements->removeElement($commandeComplement)) {
            // set the owning side to null (unless already changed)
            if ($commandeComplement->getCommandeDetail() === $this) {
                $commandeComplement->setCommandeDetail(null);
            }
        }

        return $this;
    }
}
