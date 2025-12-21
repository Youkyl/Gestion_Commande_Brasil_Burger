

using BrasilBurgerApi.Model;

namespace BrasilBurgerApi.Repository;

public interface IBurgerRepository
{
        Task<Burger?> GetByIdAsync(int id);
        Task<List<Burger>> GetAllAsync();
        Task<Burger> CreateAsync(Burger burger);
        Task<bool> ArchiveAsync(int id);
        Task<IEnumerable<Burger>> GetActiveAsync();
        
}