using BrasilBurgerApi.Model;
using Npgsql;

namespace BrasilBurgerApi.Repository;

public class MenuRepository : IMenuRepository
{
    private readonly string _connection;

    public MenuRepository(string connection)
    {
        _connection = connection;
    }

    public async Task<IEnumerable<Menu>> GetAllAsync()
    {
        var list = new List<Menu>();

        using var con = new NpgsqlConnection(_connection);
        await con.OpenAsync();

        string sql = @"SELECT id, nom, image_url, is_archive, prix, burger_id, date_creation FROM menu";

        using var cmd = new NpgsqlCommand(sql, con);
        using var reader = await cmd.ExecuteReaderAsync();

        while (await reader.ReadAsync())
        {
            list.Add(new Menu
            {
                Id = reader.GetInt32(0),
                Nom = reader.GetString(1),
                ImageUrl = reader.IsDBNull(2) ? null : reader.GetString(2),
                IsArchive = reader.GetBoolean(3),
                Prix = reader.IsDBNull(4) ? null : reader.GetDecimal(4),
                BurgerId = reader.IsDBNull(5) ? null : reader.GetInt32(5),
                DateCreation = reader.GetDateTime(6)
            });
        }

        return list;
    }
}