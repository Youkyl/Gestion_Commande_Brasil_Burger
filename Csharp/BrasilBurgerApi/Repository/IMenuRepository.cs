using BrasilBurgerApi.Model;

namespace BrasilBurgerApi.Repository;

public interface IMenuRepository
{
    Task<IEnumerable<Menu>> GetAllAsync();
}