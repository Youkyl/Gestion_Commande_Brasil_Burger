<?php

namespace App\Controller;

use App\Repository\CommandeRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;



final class CommandeController extends AbstractController
{
    #[Route(path:'/commande', name: 'commande_index')]
    public function index(CommandeRepository $commandeRepository): Response
    {
        return $this->render('commande/index.html.twig', [
            'commandes' => $commandeRepository->findAllCmd(),
        ]);
    }

    #[Route('/{id}', name: 'commande_show', requirements: ['id' => '\d+'])]
    public function show(int $id, CommandeRepository $commandeRepository): Response
    {
        $commande = $commandeRepository->find($id);

        if (!$commande) {
            throw $this->createNotFoundException('Commande introuvable');
        }

        return $this->render('commande/show.html.twig', [
            'commande' => $commande,
        ]);
    }
}
