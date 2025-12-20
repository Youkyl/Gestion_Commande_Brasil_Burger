using BrasilBurgerApi.Model;

namespace BrasilBurgerApi.Repository;

public interface ICommandeDetailRepository
{
    Task<CommandeDetail> AddDetailAsync(CommandeDetail detail);
    Task<List<CommandeDetail>> GetByCommandeIdAsync(int commandeId);
}