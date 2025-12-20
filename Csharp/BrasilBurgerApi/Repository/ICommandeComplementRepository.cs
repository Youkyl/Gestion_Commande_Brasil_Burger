using BrasilBurgerApi.Model;

namespace BrasilBurgerApi.Repository;

public interface ICommandeComplementRepository
{
    Task<CommandeComplement> AddComplementAsync(CommandeComplement complement);
}