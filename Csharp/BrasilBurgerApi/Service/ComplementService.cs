using BrasilBurgerApi.Model;
using BrasilBurgerApi.Repository;

namespace BrasilBurgerApi.Service;

public class ComplementService
{
    private readonly IComplementRepository _repo;

    public ComplementService(IComplementRepository repo)
    {
        _repo = repo;
    }

    public async Task<List<Complement>> GetAllAsync()
    {
        return await _repo.GetAllAsync();
    }

    public async Task<Complement?> GetByIdAsync(int id)
    {
        return await _repo.GetByIdAsync(id);
    }

    public async Task<Complement> CreateAsync(Complement complement)
    {
        // Complément d’un menu => pas obligé d’être en stock
        complement.IsArchive = false;
        return await _repo.CreateAsync(complement);
    }

    public async Task<bool> ArchiveAsync(int id)
    {
        return await _repo.ArchiveAsync(id);
    }

    public async Task<bool> UpdateStockAsync(int id, int newStock)
    {
        if (newStock < 0)
            throw new Exception("Le stock ne peut pas être négatif.");

        return await _repo.UpdateStockAsync(id, newStock);
    }

    public async Task<List<Complement>> GetByDetailIdAsync(int detailId)
    {
        return await _repo.GetByDetailIdAsync(detailId);
    }
}