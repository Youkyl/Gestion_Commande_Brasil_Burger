

using BrasilBurgerApi.Model;

namespace BrasilBurgerApi.Repository;

public interface IBurgerRepository
{
    Task<IEnumerable<Burger>> GetAllAsync();
}