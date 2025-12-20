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


     public async Task<List<Menu>> GetAllAsync()
        {
            var list = new List<Menu>();

            using var conn = new NpgsqlConnection(_connection);
            await conn.OpenAsync();

            string sql = "SELECT * FROM menu ORDER BY date_creation DESC";

            using var cmd = new NpgsqlCommand(sql, conn);
            using var r = await cmd.ExecuteReaderAsync();

            while (await r.ReadAsync())
            {
                list.Add(new Menu
                {
                    Id = r.GetInt32(r.GetOrdinal("id")),
                    Nom = r.GetString(r.GetOrdinal("nom")),
                    ImageUrl = r.IsDBNull(r.GetOrdinal("image_url")) ? null : r.GetString(r.GetOrdinal("image_url")),
                    Prix = r.GetDecimal(r.GetOrdinal("prix")),
                    BurgerId = r.IsDBNull(r.GetOrdinal("burger_id")) ? null : r.GetInt32(r.GetOrdinal("burger_id")),
                    IsArchive = r.GetBoolean(r.GetOrdinal("is_archive")),
                    DateCreation = r.GetDateTime(r.GetOrdinal("date_creation"))
                });
            }

            return list;
        }

        public async Task<Menu?> GetByIdAsync(int id)
        {
            using var conn = new NpgsqlConnection(_connection);
            await conn.OpenAsync();

            string sql = "SELECT * FROM menu WHERE id=@id";

            using var cmd = new NpgsqlCommand(sql, conn);
            cmd.Parameters.AddWithValue("@id", id);

            using var r = await cmd.ExecuteReaderAsync();

            if (!await r.ReadAsync()) return null;

            return new Menu
            {
                Id = r.GetInt32(r.GetOrdinal("id")),
                Nom = r.GetString(r.GetOrdinal("nom")),
                ImageUrl = r.IsDBNull(r.GetOrdinal("image_url")) ? null : r.GetString(r.GetOrdinal("image_url")),
                Prix = r.GetDecimal(r.GetOrdinal("prix")),
                BurgerId = r.IsDBNull(r.GetOrdinal("burger_id")) ? null : r.GetInt32(r.GetOrdinal("burger_id")),
                IsArchive = r.GetBoolean(r.GetOrdinal("is_archive")),
                DateCreation = r.GetDateTime(r.GetOrdinal("date_creation"))
            };
        }

        public async Task<Menu> CreateAsync(Menu menu)
        {
            using var conn = new NpgsqlConnection(_connection);
            await conn.OpenAsync();

            string sql = @"
                INSERT INTO menu (nom, image_url, prix, burger_id)
                VALUES (@nom, @image, @prix, @burger)
                RETURNING id, date_creation, is_archive;
            ";

            using var cmd = new NpgsqlCommand(sql, conn);
            cmd.Parameters.AddWithValue("@nom", menu.Nom);
            cmd.Parameters.AddWithValue("@image", (object?)menu.ImageUrl ?? DBNull.Value);
            cmd.Parameters.AddWithValue("@prix", menu.Prix);
            cmd.Parameters.AddWithValue("@burger", (object?)menu.BurgerId ?? DBNull.Value);

            using var r = await cmd.ExecuteReaderAsync();
            if (await r.ReadAsync())
            {
                menu.Id = r.GetInt32(0);
                menu.DateCreation = r.GetDateTime(1);
                menu.IsArchive = r.GetBoolean(2);
            }

            return menu;
        }

        public async Task<bool> ArchiveAsync(int id)
        {
            using var conn = new NpgsqlConnection(_connection);
            await conn.OpenAsync();

            string sql = "UPDATE menu SET is_archive = TRUE WHERE id=@id";

            using var cmd = new NpgsqlCommand(sql, conn);
            cmd.Parameters.AddWithValue("@id", id);

            return await cmd.ExecuteNonQueryAsync() > 0;
        }



    public async Task<List<Menu>> GetActiveAsync()
    {
        var menus = new List<Menu>();
        using var conn = new NpgsqlConnection(_connection);
        await conn.OpenAsync();

        const string query = "SELECT * FROM menu WHERE is_archive = FALSE";
        using var cmd = new NpgsqlCommand(query, conn);

        using var reader = await cmd.ExecuteReaderAsync();
        while (await reader.ReadAsync())
        {
            menus.Add(new Menu
            {
                Id = reader.GetInt32(reader.GetOrdinal("id")),
                Nom = reader.GetString(reader.GetOrdinal("nom")),
                ImageUrl = reader["image_url"] as string ?? "",
                IsArchive = reader.GetBoolean(reader.GetOrdinal("is_archive")),
                Prix = reader["prix"] == DBNull.Value ? 0 : reader.GetDecimal(reader.GetOrdinal("prix")),
                BurgerId = reader["burger_id"] == DBNull.Value ? null : reader.GetInt32(reader.GetOrdinal("burger_id")),
                DateCreation = reader["date_creation"] == DBNull.Value
                    ? DateTime.Now : reader.GetDateTime(reader.GetOrdinal("date_creation"))
            });
        }

        return menus;
    }

} 