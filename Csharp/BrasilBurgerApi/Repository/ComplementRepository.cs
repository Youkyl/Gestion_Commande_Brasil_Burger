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

     public async Task<List<Complement>> GetAllAsync()
        {
            var list = new List<Complement>();

            using var conn = new NpgsqlConnection(_connection);
            await conn.OpenAsync();

            string sql = "SELECT * FROM complement ORDER BY id DESC";

            using var cmd = new NpgsqlCommand(sql, conn);
            using var r = await cmd.ExecuteReaderAsync();

            while (await r.ReadAsync())
            {
                list.Add(new Complement
                {
                    Id = r.GetInt32(r.GetOrdinal("id")),
                    Nom = r.GetString(r.GetOrdinal("nom")),
                    Prix = r.GetDecimal(r.GetOrdinal("prix")),
                    ImageUrl = r.IsDBNull(r.GetOrdinal("image_url")) ? null : r.GetString(r.GetOrdinal("image_url")),
                    IsArchive = r.GetBoolean(r.GetOrdinal("is_archive")),
                    Type = r.GetString(r.GetOrdinal("type")),
                    Stock = r.GetInt32(r.GetOrdinal("stock"))
                });
            }

            return list;
        }

        public async Task<Complement?> GetByIdAsync(int id)
        {
            using var conn = new NpgsqlConnection(_connection);
            await conn.OpenAsync();

            string sql = "SELECT * FROM complement WHERE id=@id";

            using var cmd = new NpgsqlCommand(sql, conn);
            cmd.Parameters.AddWithValue("@id", id);

            using var r = await cmd.ExecuteReaderAsync();

            if (!await r.ReadAsync()) return null;

            return new Complement
            {
                Id = r.GetInt32(r.GetOrdinal("id")),
                Nom = r.GetString(r.GetOrdinal("nom")),
                Prix = r.GetDecimal(r.GetOrdinal("prix")),
                ImageUrl = r.IsDBNull(r.GetOrdinal("image_url")) ? null : r.GetString(r.GetOrdinal("image_url")),
                IsArchive = r.GetBoolean(r.GetOrdinal("is_archive")),
                Type = r.GetString(r.GetOrdinal("type")),
                Stock = r.GetInt32(r.GetOrdinal("stock"))
            };
        }

        public async Task<Complement> CreateAsync(Complement comp)
        {
            using var conn = new NpgsqlConnection(_connection);
            await conn.OpenAsync();

            string sql = @"
                INSERT INTO complement (nom, prix, image_url, type, stock)
                VALUES (@nom, @prix, @image, @type, @stock)
                RETURNING id;
            ";

            using var cmd = new NpgsqlCommand(sql, conn);

            cmd.Parameters.AddWithValue("@nom", comp.Nom);
            cmd.Parameters.AddWithValue("@prix", comp.Prix);
            cmd.Parameters.AddWithValue("@image", (object?)comp.ImageUrl ?? DBNull.Value);
            cmd.Parameters.AddWithValue("@type", comp.Type);
            cmd.Parameters.AddWithValue("@stock", comp.Stock);

            comp.Id = (int)await cmd.ExecuteScalarAsync();
            return comp;
        }

        public async Task<bool> ArchiveAsync(int id)
        {
            using var conn = new NpgsqlConnection(_connection);
            await conn.OpenAsync();

            string sql = "UPDATE complement SET is_archive = TRUE WHERE id=@id";

            using var cmd = new NpgsqlCommand(sql, conn);
            cmd.Parameters.AddWithValue("@id", id);

            return await cmd.ExecuteNonQueryAsync() > 0;
        }


    public async Task<List<Complement>> GetByDetailIdAsync(int detailId)
{
    var list = new List<Complement>();

    using var conn = new NpgsqlConnection(_connection);
    await conn.OpenAsync();

    string sql = @"
        SELECT c.id, c.nom, c.prix, c.image_url, c.is_archive, c.type, c.stock,
               cc.quantite, cc.prix_unitaire
        FROM commande_complement cc
        JOIN complement c ON cc.complement_id = c.id
        WHERE cc.commande_detail_id = @detailId
        ORDER BY cc.id;
    ";

    using var cmd = new NpgsqlCommand(sql, conn);
    cmd.Parameters.AddWithValue("@detailId", detailId);

    using var r = await cmd.ExecuteReaderAsync();

    while (await r.ReadAsync())
    {
        var comp = new Complement
        {
            Id = r.GetInt32(r.GetOrdinal("id")),
            Nom = r.GetString(r.GetOrdinal("nom")),
            Prix = r.GetDecimal(r.GetOrdinal("prix")),
            ImageUrl = r.IsDBNull(r.GetOrdinal("image_url")) ? null : r.GetString(r.GetOrdinal("image_url")),
            IsArchive = r.GetBoolean(r.GetOrdinal("is_archive")),
            Type = r.GetString(r.GetOrdinal("type")),
            Stock = r.GetInt32(r.GetOrdinal("stock"))
        };

        // Ajout des infos propres à la relation commande_complement
        comp.RelationQuantite = r.GetInt32(r.GetOrdinal("quantite"));
        comp.RelationPrixUnitaire = r.GetDecimal(r.GetOrdinal("prix_unitaire"));

        list.Add(comp);
    }

    return list;
}

    public async Task<bool> UpdateStockAsync(int id, int stock)
    {
        using var conn = new NpgsqlConnection(_connection);
        await conn.OpenAsync();

        const string query = "UPDATE complement SET stock = @stock WHERE id = @id";
        using var cmd = new NpgsqlCommand(query, conn);

        cmd.Parameters.AddWithValue("@id", id);
        cmd.Parameters.AddWithValue("@stock", stock);

        return await cmd.ExecuteNonQueryAsync() > 0;
    }
}