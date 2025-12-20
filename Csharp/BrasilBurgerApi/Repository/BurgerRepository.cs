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


    public async Task<Burger?> GetByIdAsync(int id)
        {
            using var conn = new NpgsqlConnection(_connection);
            await conn.OpenAsync();

            string sql = "SELECT * FROM burger WHERE id=@id";

            using var cmd = new NpgsqlCommand(sql, conn);
            cmd.Parameters.AddWithValue("@id", id);

            using var r = await cmd.ExecuteReaderAsync();

            if (!await r.ReadAsync())
                return null;

            return new Burger
            {
                Id = r.GetInt32(r.GetOrdinal("id")),
                Nom = r.GetString(r.GetOrdinal("nom")),
                Ingredient = r.IsDBNull(r.GetOrdinal("ingredient")) ? null : r.GetString(r.GetOrdinal("ingredient")),
                Prix = r.GetDecimal(r.GetOrdinal("prix")),
                ImageUrl = r.IsDBNull(r.GetOrdinal("image_url")) ? null : r.GetString(r.GetOrdinal("image_url")),
                IsArchive = r.GetBoolean(r.GetOrdinal("is_archive")),
                DateCreation = r.GetDateTime(r.GetOrdinal("date_creation"))
            };
        }

        public async Task<List<Burger>> GetAllAsync()
        {
            var list = new List<Burger>();

            using var conn = new NpgsqlConnection(_connection);
            await conn.OpenAsync();

            string sql = "SELECT * FROM burger ORDER BY date_creation DESC";

            using var cmd = new NpgsqlCommand(sql, conn);
            using var r = await cmd.ExecuteReaderAsync();

            while (await r.ReadAsync())
            {
                list.Add(new Burger
                {
                    Id = r.GetInt32(r.GetOrdinal("id")),
                    Nom = r.GetString(r.GetOrdinal("nom")),
                    Ingredient = r.IsDBNull(r.GetOrdinal("ingredient")) ? null : r.GetString(r.GetOrdinal("ingredient")),
                    Prix = r.GetDecimal(r.GetOrdinal("prix")),
                    ImageUrl = r.IsDBNull(r.GetOrdinal("image_url")) ? null : r.GetString(r.GetOrdinal("image_url")),
                    IsArchive = r.GetBoolean(r.GetOrdinal("is_archive")),
                    DateCreation = r.GetDateTime(r.GetOrdinal("date_creation"))
                });
            }

            return list;
        }

        public async Task<Burger> CreateAsync(Burger burger)
        {
            using var conn = new NpgsqlConnection(_connection);
            await conn.OpenAsync();

            string sql = @"
                INSERT INTO burger(nom, ingredient, prix, image_url)
                VALUES(@nom, @ing, @prix, @url)
                RETURNING id, date_creation, is_archive;
            ";

            using var cmd = new NpgsqlCommand(sql, conn);

            cmd.Parameters.AddWithValue("@nom", burger.Nom);
            cmd.Parameters.AddWithValue("@ing", (object?)burger.Ingredient ?? DBNull.Value);
            cmd.Parameters.AddWithValue("@prix", burger.Prix);
            cmd.Parameters.AddWithValue("@url", (object?)burger.ImageUrl ?? DBNull.Value);

            using var r = await cmd.ExecuteReaderAsync();
            if (await r.ReadAsync())
            {
                burger.Id = r.GetInt32(0);
                burger.DateCreation = r.GetDateTime(1);
                burger.IsArchive = r.GetBoolean(2);
            }

            return burger;
        }

        public async Task<bool> ArchiveAsync(int id)
        {
            using var conn = new NpgsqlConnection(_connection);
            await conn.OpenAsync();

            string sql = "UPDATE burger SET is_archive = TRUE WHERE id = @id";

            using var cmd = new NpgsqlCommand(sql, conn);
            cmd.Parameters.AddWithValue("@id", id);

            return await cmd.ExecuteNonQueryAsync() > 0;
        }


public async Task<IEnumerable<Burger>> GetActiveAsync()
    {
        var list = new List<Burger>();
        using var conn = new NpgsqlConnection(_connection);
        await conn.OpenAsync();

        var cmd = new NpgsqlCommand("SELECT * FROM burger WHERE is_archive = FALSE", conn);
        using var reader = await cmd.ExecuteReaderAsync();

        while (await reader.ReadAsync())
        {
            list.Add(Map(reader));
        }
        return list;
    }

    private Burger Map(NpgsqlDataReader reader)
    {
        throw new NotImplementedException();
    }
}