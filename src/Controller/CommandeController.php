<?php

namespace App\Controller;

use App\Entity\HistoriqueEtatCommande;
use App\Repository\CommandeRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
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

    #[Route('/{id}/etat', name: 'commande_change_etat', methods: ['POST'])]
    public function changeEtat(
        int $id,
        Request $request,
        CommandeRepository $commandeRepository,
        EntityManagerInterface $em
    ): Response {
        $commande = $commandeRepository->find($id);

        if (!$commande) {
            throw $this->createNotFoundException();
        }

        $ancienEtat = $commande->getEtat();
        $nouvelEtat = $request->request->get('etat');

        $commande->setEtat($nouvelEtat);

        $historique = new HistoriqueEtatCommande();
        $historique->setCommande($commande);
        $historique->setAncienEtat($ancienEtat);
        $historique->setNouvelEtat($nouvelEtat);
        $historique->setDateChangement(new \DateTime());
        $historique->setGestionnaire($this->getUser());

        $em->persist($historique);
        $em->flush();

        return $this->redirectToRoute('commande_show', ['id' => $id]);
    }
}
