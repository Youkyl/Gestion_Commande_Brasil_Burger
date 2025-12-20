using BrasilBurgerApi.Model;

namespace BrasilBurgerApi.Repository;

public interface IClientRepository
{
    Task<Client?> FindByEmailAsync(string email);
    Task<Client> CreateAsync(Client client);
}