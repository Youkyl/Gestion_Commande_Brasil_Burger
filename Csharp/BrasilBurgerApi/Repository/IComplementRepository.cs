using BrasilBurgerApi.Model;

namespace BrasilBurgerApi.Repository;

public interface IComplementRepository
{
    Task<IEnumerable<Complement>> GetAllAsync();
}