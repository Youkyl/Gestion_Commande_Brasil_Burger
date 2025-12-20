using BrasilBurgerApi.Model;

namespace BrasilBurgerApi.Repository;

public interface ICommandeRepository
{
    Task<Commande> CreateCommandeAsync(Commande commande);
    Task<Commande?> GetCommandeByReferenceAsync(string reference);
    Task UpdateMontantTotalAsync(int commandeId, decimal montantTotal);
}