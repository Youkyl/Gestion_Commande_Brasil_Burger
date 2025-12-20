using BrasilBurgerApi.Model;
using Npgsql;

namespace BrasilBurgerApi.Repository;

public class ClientRepository : IClientRepository
{
    private readonly string _connectionString;

    public ClientRepository(string connectionString)
    {
        _connectionString = connectionString;
    }

    public async Task<Client?> FindByEmailAsync(string email)
    {
        using var connection = new NpgsqlConnection(_connectionString);
        await connection.OpenAsync();

        const string query = @"SELECT * FROM client WHERE email = @Email LIMIT 1";

        using var cmd = new NpgsqlCommand(query, connection);
        cmd.Parameters.AddWithValue("@Email", email);

        using var reader = await cmd.ExecuteReaderAsync();

        if (await reader.ReadAsync())
        {
            return new Client
            {
                Id = reader.GetInt32(reader.GetOrdinal("id")),
                Nom = reader.GetString(reader.GetOrdinal("nom")),
                Prenom = reader.GetString(reader.GetOrdinal("prenom")),
                Telephone = reader.GetString(reader.GetOrdinal("telephone")),
                Email = reader.GetString(reader.GetOrdinal("email")),
                Password = reader.GetString(reader.GetOrdinal("password")),
                Adresse = reader.IsDBNull(reader.GetOrdinal("adresse")) 
                            ? null 
                            : reader.GetString(reader.GetOrdinal("adresse")),
                IsActif = reader.GetBoolean(reader.GetOrdinal("is_actif")),
                DateCreation = reader.GetDateTime(reader.GetOrdinal("date_creation")),
            };
        }

        return null;
    }

    public async Task<Client> CreateAsync(Client client)
    {
        using var connection = new NpgsqlConnection(_connectionString);
        await connection.OpenAsync();

        const string query = @"
            INSERT INTO client (nom, prenom, telephone, email, password, adresse)
            VALUES (@Nom, @Prenom, @Telephone, @Email, @Password, @Adresse)
            RETURNING id, date_creation";

        using var cmd = new NpgsqlCommand(query, connection);

        cmd.Parameters.AddWithValue("@Nom", client.Nom);
        cmd.Parameters.AddWithValue("@Prenom", client.Prenom);
        cmd.Parameters.AddWithValue("@Telephone", client.Telephone);
        cmd.Parameters.AddWithValue("@Email", client.Email);
        cmd.Parameters.AddWithValue("@Password", client.Password);
        cmd.Parameters.AddWithValue("@Adresse", (object?)client.Adresse ?? DBNull.Value);

        using var reader = await cmd.ExecuteReaderAsync();
        if (await reader.ReadAsync())
        {
            client.Id = reader.GetInt32(0);
            client.DateCreation = reader.GetDateTime(1);
        }

        return client;
    }
}