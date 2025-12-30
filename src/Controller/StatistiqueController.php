<?php

namespace App\Controller;

use App\Repository\CommandeDetailRepository;
use App\Repository\CommandeRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

final class StatistiqueController extends AbstractController
{

    #[Route('/statistique', name: 'gestionnaire_statistiques')]
    public function dashboard(
        CommandeRepository $commandeRepo,
        CommandeDetailRepository $detailRepo
    ): Response {
        return $this->render('statistique/index.html.twig', [
            'dateJour' => new \DateTime(),
            'enCours' => $commandeRepo->countByEtatToday('EN_COURS'),
            'validees' => $commandeRepo->countByEtatToday('PAYEE'),
            'annulees' => $commandeRepo->countByEtatToday('ANNULEE'),
            'recettes' => $commandeRepo->recettesJour(),
            'topProduits' => $detailRepo->topProduitsJour(),
            'recentes' => $commandeRepo->findBy(
                [],
                ['date_commande' => 'DESC'],
                5
            ),
        ]);
    }
}
