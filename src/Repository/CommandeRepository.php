<?php

namespace App\Repository;

use App\Entity\Commande;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Commande>
 */
class CommandeRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Commande::class);
    }

//    /**
//     * @return Commande[] Returns an array of Commande objects
//     */
//    public function findByExampleField($value): array
//    {
//        return $this->createQueryBuilder('c')
//            ->andWhere('c.exampleField = :val')
//            ->setParameter('val', $value)
//            ->orderBy('c.id', 'ASC')
//            ->setMaxResults(10)
//            ->getQuery()
//            ->getResult()
//        ;
//    }

//    public function findOneBySomeField($value): ?Commande
//    {
//        return $this->createQueryBuilder('c')
//            ->andWhere('c.exampleField = :val')
//            ->setParameter('val', $value)
//            ->getQuery()
//            ->getOneOrNullResult()
//        ;
//    }


    public function findAllCmd(): array
    {
        return $this->createQueryBuilder('c')
            ->join('c.client', 'cl')
            ->addSelect('cl')
            ->orderBy('c.date_commande', 'DESC')
            ->getQuery()
            ->getResult();
    }

    public function countByEtatToday(string $etat): int
    {
        $debut = new \DateTime('today 00:00:00');
        $fin = new \DateTime('today 23:59:59');

        return (int) $this->createQueryBuilder('c')
            ->select('COUNT(c.id)')
            ->where('c.etat = :etat')
            ->andWhere('c.date_commande BETWEEN :debut AND :fin')
            ->setParameter('etat', $etat)
            ->setParameter('debut', $debut)
            ->setParameter('fin', $fin)
            ->getQuery()
            ->getSingleScalarResult();
    }

    public function recettesJour(): float
    {
        $debut = new \DateTime('today 00:00:00');
        $fin = new \DateTime('today 23:59:59');

        return (float) $this->createQueryBuilder('c')
            ->select('SUM(c.montant_total)')
            ->where('c.etat = :etat')
            ->andWhere('c.date_commande BETWEEN :debut AND :fin')
            ->setParameter('etat', 'PAYEE')
            ->setParameter('debut', $debut)
            ->setParameter('fin', $fin)
            ->getQuery()
            ->getSingleScalarResult();
    }
}
