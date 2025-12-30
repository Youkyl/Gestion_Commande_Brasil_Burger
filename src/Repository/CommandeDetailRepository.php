<?php

namespace App\Repository;

use App\Entity\CommandeDetail;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<CommandeDetail>
 */
class CommandeDetailRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, CommandeDetail::class);
    }

//    /**
//     * @return CommandeDetail[] Returns an array of CommandeDetail objects
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

//    public function findOneBySomeField($value): ?CommandeDetail
//    {
//        return $this->createQueryBuilder('c')
//            ->andWhere('c.exampleField = :val')
//            ->setParameter('val', $value)
//            ->getQuery()
//            ->getOneOrNullResult()
//        ;
//    }

    public function topProduitsJour(): array
    {
        $debut = new \DateTime('today 00:00:00');
        $fin = new \DateTime('today 23:59:59');

        return $this->createQueryBuilder('d')
            ->select('
                COALESCE(b.nom, m.nom) AS produit,
                SUM(d.quantite) AS total
            ')
            ->leftJoin('d.burger', 'b')
            ->leftJoin('d.menu', 'm')
            ->join('d.commande', 'c')
            ->where('c.date_commande BETWEEN :debut AND :fin')
            ->setParameter('debut', $debut)
            ->setParameter('fin', $fin)
            ->groupBy('produit')
            ->orderBy('total', 'DESC')
            ->setMaxResults(5)
            ->getQuery()
            ->getResult();
    }
}
