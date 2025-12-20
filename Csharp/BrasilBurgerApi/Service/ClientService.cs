using BCrypt.Net;
using BrasilBurgerApi.DTO;
using BrasilBurgerApi.Model;
using BrasilBurgerApi.Repository;

namespace BrasilBurgerApi.Service;

public class ClientService
{
    private readonly IClientRepository _repo;

    public ClientService(IClientRepository repo)
    {
        _repo = repo;
    }

    public async Task<Client> RegisterAsync(RegisterClientDto dto)
    {
        var existing = await _repo.FindByEmailAsync(dto.Email);
        if (existing != null)
            throw new Exception("Email déjà utilisé.");

        var hashedPassword = BCrypt.Net.BCrypt.HashPassword(dto.Password);

        var client = new Client
        {
            Nom = dto.Nom,
            Prenom = dto.Prenom,
            Telephone = dto.Telephone,
            Email = dto.Email,
            Password = hashedPassword,
            Adresse = dto.Adresse
        };

        return await _repo.CreateAsync(client);
    }
}