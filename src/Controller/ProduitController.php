<?php

namespace App\Controller;

use App\Repository\BurgerRepository;
use App\Repository\ComplementRepository;
use App\Repository\MenuRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

final class ProduitController extends AbstractController
{
    #[Route('/produit', name: 'produit')]
    public function index(
        BurgerRepository $burgerRepo,
        MenuRepository $menuRepo,
        ComplementRepository $complementRepo
    ): Response {

        // ⚠️ On ne modifie PAS la base, on adapte Symfony
        $produits = [];

        // FILTRE : uniquement produits actifs
        $criteria = ['isArchive' => false];

        // ===== BURGERS =====
        foreach ($burgerRepo->findBy($criteria) as $b) {
            $produits[] = [
                'type' => 'Burger',
                'nom' => $b->getNom(),
                'description' => $b->getIngredient(),
                'prix' => $b->getPrix(),
                'image' => $b->getImageUrl() ?: '/images/placeholder.jpg',
            ];
        }

        // ===== MENUS =====
        foreach ($menuRepo->findBy($criteria) as $m) {
            $produits[] = [
                'type' => 'Menu',
                'nom' => $m->getNom(),
                'description' => 'Menu complet',
                'prix' => $m->getPrix(),
                'image' => $m->getImageUrl() ?: '/images/placeholder.jpg',
            ];
        }

        // ===== COMPLEMENTS =====
        foreach ($complementRepo->findBy($criteria) as $c) {
            $produits[] = [
                'type' => 'Complément',
                'nom' => $c->getNom(),
                'description' => $c->getType(),
                'prix' => $c->getPrix(),
                'image' => $c->getImageUrl() ?: '/images/placeholder.jpg',
            ];
        }

        return $this->render('produit/index.html.twig', [
            'produits' => $produits
        ]);
    }


    #[Route('/produit/burger', name: 'burger')]
    public function burger(
        BurgerRepository $burgerRepo,
    ): Response {

        // ⚠️ On ne modifie PAS la base, on adapte Symfony
        $burger = [];

        // FILTRE : uniquement produits actifs
        $criteria = ['isArchive' => false];

        // ===== BURGERS =====
        foreach ($burgerRepo->findBy($criteria) as $b) {
            $burger[] = [
                'type' => 'Burger',
                'nom' => $b->getNom(),
                'description' => $b->getIngredient(),
                'prix' => $b->getPrix(),
                'image' => $b->getImageUrl() ?: '/images/placeholder.jpg',
            ];
        }

        return $this->render('produit/burger.html.twig', [
            'burger' => $burger
        ]);
    }

    #[Route('/produit/menu', name: 'menu')]
    public function menu(
        MenuRepository $menuRepo,
    ): Response {

        // ⚠️ On ne modifie PAS la base, on adapte Symfony
        $menu = [];

        // FILTRE : uniquement produits actifs
        $criteria = ['isArchive' => false];

        // ===== MENUS =====
        foreach ($menuRepo->findBy($criteria) as $m) {
            $menu[] = [
                'type' => 'Menu',
                'nom' => $m->getNom(),
                'description' => 'Menu complet',
                'prix' => $m->getPrix(),
                'image' => $m->getImageUrl() ?: '/images/placeholder.jpg',
            ];
        }

        return $this->render('produit/menu.html.twig', [
            'menu' => $menu
        ]);
    }


    #[Route('/produit/complement', name: 'complement')]
    public function complement(
        ComplementRepository $complementRepo,
    ): Response {

        // ⚠️ On ne modifie PAS la base, on adapte Symfony
        $complement = [];

        // FILTRE : uniquement produits actifs
        $criteria = ['isArchive' => false];

        // ===== COMPLEMENTS =====
        foreach ($complementRepo->findBy($criteria) as $c) {
            $complement[] = [
                'type' => 'Complément',
                'nom' => $c->getNom(),
                'description' => $c->getType(),
                'prix' => $c->getPrix(),
                'image' => $c->getImageUrl() ?: '/images/placeholder.jpg',
            ];
        }

        return $this->render('produit/complement.html.twig', [
            'complement' => $complement
        ]);
    }




}