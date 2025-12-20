using BrasilBurgerApi.Model;

namespace BrasilBurgerApi.Repository;

public interface IComplementRepository
{
    

    Task<List<Complement>> GetAllAsync();
    Task<Complement?> GetByIdAsync(int id);
    Task<Complement> CreateAsync(Complement c);
    Task<bool> ArchiveAsync(int id);
    Task<bool> UpdateStockAsync(int id, int stock);
    Task<List<Complement>> GetByDetailIdAsync(int detailId);
}