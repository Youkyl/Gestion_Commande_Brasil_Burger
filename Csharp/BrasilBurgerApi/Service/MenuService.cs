using BrasilBurgerApi.Model;
using BrasilBurgerApi.Repository;

namespace BrasilBurgerApi.Service;

public class MenuService
{
    private readonly IMenuRepository _repo;

    public MenuService(IMenuRepository repo)
    {
        _repo = repo;
    }

    public async Task<List<Menu>> GetAllAsync()
    {
        return await _repo.GetAllAsync();
    }

    public async Task<Menu?> GetByIdAsync(int id)
    {
        return await _repo.GetByIdAsync(id);
    }

    public async Task<Menu> CreateAsync(Menu menu)
    {
        menu.DateCreation = DateTime.Now;
        menu.IsArchive = false;

        return await _repo.CreateAsync(menu);
    }

    public async Task<bool> ArchiveAsync(int id)
    {
        return await _repo.ArchiveAsync(id);
    }

    public async Task<List<Menu>> GetActiveAsync()
    {
        return await _repo.GetActiveAsync();
    }
}