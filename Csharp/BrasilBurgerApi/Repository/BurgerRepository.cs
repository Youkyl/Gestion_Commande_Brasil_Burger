using BrasilBurgerApi.Model;
using Npgsql;

namespace BrasilBurgerApi.Repository;

public class BurgerRepository : IBurgerRepository
{
    private readonly string _connection;

    public BurgerRepository(string connectionString)
    {
        _connection = connectionString;
    }

    public async Task<IEnumerable<Burger>> GetAllAsync()
    {
        var result = new List<Burger>();

        using var con = new NpgsqlConnection(_connection);
        await con.OpenAsync();

        var sql = @"SELECT id, nom, ingredient, prix, image_url, is_archive, date_creation FROM burger";

        using var cmd = new NpgsqlCommand(sql, con);
        using var reader = await cmd.ExecuteReaderAsync();

        while (await reader.ReadAsync())
        {
            result.Add(new Burger
            {
                Id = reader.GetInt32(0),
                Nom = reader.GetString(1),
                Ingredient = reader.IsDBNull(2) ? null : reader.GetString(2),
                Prix = reader.GetDecimal(3),
                ImageUrl = reader.IsDBNull(4) ? null : reader.GetString(4),
                IsArchive = reader.GetBoolean(5),
                DateCreation = reader.GetDateTime(6)
            });
        }

        return result;
    }
}