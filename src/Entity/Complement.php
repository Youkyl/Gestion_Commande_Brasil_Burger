<?php

namespace App\Entity;

use App\Repository\ComplementRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: ComplementRepository::class)]
#[ORM\Table(name: '`complement`')]
class Complement
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(length: 150)]
    private ?string $nom = null;

    #[ORM\Column(type: Types::DECIMAL, precision: 10, scale: 2)]
    private ?string $prix = null;

    #[ORM\Column(length: 50)]
    private ?string $type = null;

    #[ORM\Column]
    private ?int $stock = null;

    /**
     * @var Collection<int, CommandeComplement>
     */
    #[ORM\OneToMany(targetEntity: CommandeComplement::class, mappedBy: 'complement')]
    private Collection $commandeComplements;

    #[ORM\Column(type: Types::TEXT, nullable: true)]
    private ?string $image_url = null;

    #[ORM\Column]
    private ?bool $isArchive = null;

    public function __construct()
    {
        $this->commandeComplements = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getNom(): ?string
    {
        return $this->nom;
    }

    public function setNom(string $nom): static
    {
        $this->nom = $nom;

        return $this;
    }

    public function getPrix(): ?string
    {
        return $this->prix;
    }

    public function setPrix(string $prix): static
    {
        $this->prix = $prix;

        return $this;
    }

    public function getType(): ?string
    {
        return $this->type;
    }

    public function setType(string $type): static
    {
        $this->type = $type;

        return $this;
    }

    public function getStock(): ?int
    {
        return $this->stock;
    }

    public function setStock(int $stock): static
    {
        $this->stock = $stock;

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
            $commandeComplement->setComplement($this);
        }

        return $this;
    }

    public function removeCommandeComplement(CommandeComplement $commandeComplement): static
    {
        if ($this->commandeComplements->removeElement($commandeComplement)) {
            // set the owning side to null (unless already changed)
            if ($commandeComplement->getComplement() === $this) {
                $commandeComplement->setComplement(null);
            }
        }

        return $this;
    }

    public function getImageUrl(): ?string
    {
        return $this->image_url;
    }

    public function setImageUrl(?string $image_url): static
    {
        $this->image_url = $image_url;

        return $this;
    }

    public function isArchive(): ?bool
    {
        return $this->isArchive;
    }

    public function setIsArchive(bool $isArchive): static
    {
        $this->isArchive = $isArchive;

        return $this;
    }
}
