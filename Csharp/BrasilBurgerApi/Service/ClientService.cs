using BCrypt.Net;
using BrasilBurgerApi.DTO;
using BrasilBurgerApi.Model;
using BrasilBurgerApi.Repository;
using System.IdentityModel.Tokens.Jwt;
using Microsoft.IdentityModel.Tokens;
using System.Text;
using System.Security.Claims;

namespace BrasilBurgerApi.Service;

public class ClientService
{
    private readonly IClientRepository _repo;
    private readonly IConfiguration _config;

    public ClientService(IClientRepository repo, IConfiguration config)
    {
        _repo = repo;
        _config = config;
    }

    public async Task<Client> RegisterAsync(RegisterClientDto dto)
    {
        // Vérifier si l'email existe déjà
        var existing = await _repo.FindByEmailAsync(dto.Email);
        if (existing != null)
            throw new Exception("Email déjà utilisé.");

        // Hash du mot de passe
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

    public async Task<LoginResponse?> LoginAsync(LoginRequest request)
    {
        var client = await _repo.FindByEmailAsync(request.Email);

        if (client == null)
            return null;

        // Vérifier le mot de passe
        if (!BCrypt.Net.BCrypt.Verify(request.Password, client.Password))
            return null;

        // Clé de signature
        var key = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(_config["Jwt:Key"]!));
        var creds = new SigningCredentials(key, SecurityAlgorithms.HmacSha256);

        var claims = new[]
        {
            new Claim(JwtRegisteredClaimNames.Sub, client.Id.ToString()),
            new Claim(JwtRegisteredClaimNames.Email, client.Email),
            new Claim("name", client.Nom + " " + client.Prenom)
        };

        var token = new JwtSecurityToken(
            issuer: _config["Jwt:Issuer"],
            audience: _config["Jwt:Audience"],
            claims: claims,
            expires: DateTime.Now.AddMinutes(int.Parse(_config["Jwt:ExpiresInMinutes"]!)),
            signingCredentials: creds
        );

        return new LoginResponse
        {
            Token = new JwtSecurityTokenHandler().WriteToken(token),
            FullName = client.Nom + " " + client.Prenom,
            ClientId = client.Id
        };
    }
}