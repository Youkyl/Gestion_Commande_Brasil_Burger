using BrasilBurgerApi.Model;
using Npgsql;

namespace BrasilBurgerApi.Repository;

public class ComplementRepository : IComplementRepository
{
    private readonly string _connection;

    public ComplementRepository(string connection)
    {
        _connection = connection;
    }

    public async Task<IEnumerable<Complement>> GetAllAsync()
    {
        var list = new List<Complement>();

        using var con = new NpgsqlConnection(_connection);
        await con.OpenAsync();

        string sql = @"SELECT id, nom, prix, image_url, is_archive, type, stock FROM complement";

        using var cmd = new NpgsqlCommand(sql, con);
        using var reader = await cmd.ExecuteReaderAsync();

        while (await reader.ReadAsync())
        {
            list.Add(new Complement
            {
                Id = reader.GetInt32(0),
                Nom = reader.GetString(1),
                Prix = reader.GetDecimal(2),
                ImageUrl = reader.IsDBNull(3) ? null : reader.GetString(3),
                IsArchive = reader.GetBoolean(4),
                Type = reader.GetString(5),
                Stock = reader.GetInt32(6)
            });
        }

        return list;
    }
}