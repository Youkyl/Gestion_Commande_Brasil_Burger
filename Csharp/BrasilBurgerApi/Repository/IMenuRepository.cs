using BrasilBurgerApi.Model;

namespace BrasilBurgerApi.Repository;

public interface IMenuRepository
{


    Task<List<Menu>> GetAllAsync();
    Task<List<Menu>> GetActiveAsync();
    Task<Menu?> GetByIdAsync(int id);
    Task<Menu> CreateAsync(Menu menu);
    Task<bool> ArchiveAsync(int id);
}