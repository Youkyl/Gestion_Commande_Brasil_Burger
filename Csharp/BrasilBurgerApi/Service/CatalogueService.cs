using BrasilBurgerApi.Model;
using BrasilBurgerApi.Repository;

namespace BrasilBurgerApi.Service;

public class CatalogueService
{
    private readonly IBurgerRepository _burgerRepo;
    private readonly IMenuRepository _menuRepo;
    private readonly IComplementRepository _complementRepo;

    public CatalogueService(
        IBurgerRepository burgerRepo,
        IMenuRepository menuRepo,
        IComplementRepository complementRepo)
    {
        _burgerRepo = burgerRepo;
        _menuRepo = menuRepo;
        _complementRepo = complementRepo;
    }

    public Task<IEnumerable<Burger>> GetBurgersAsync() => _burgerRepo.GetAllAsync();
    public Task<IEnumerable<Menu>> GetMenusAsync() => _menuRepo.GetAllAsync();
    public Task<IEnumerable<Complement>> GetComplementsAsync() => _complementRepo.GetAllAsync();
}