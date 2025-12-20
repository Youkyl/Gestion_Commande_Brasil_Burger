using BrasilBurgerApi.Model;
using Npgsql;

namespace BrasilBurgerApi.Repository;

public class CommandeDetailRepository : ICommandeDetailRepository
{
    private readonly string _connectionString;

    public CommandeDetailRepository(string connectionString)
    {
        _connectionString = connectionString;
    }

    public async Task<CommandeDetail> AddDetailAsync(CommandeDetail detail)
    {
        using var conn = new NpgsqlConnection(_connectionString);
        await conn.OpenAsync();

        const string sql = @"
            INSERT INTO commande_detail (commande_id, burger_id, menu_id, quantite, prix_unitaire)
            VALUES (@cmd, @burger, @menu, @qte, @prix)
            RETURNING id;
        ";

        using var cmd = new NpgsqlCommand(sql, conn);

        cmd.Parameters.AddWithValue("@cmd", detail.CommandeId);

        cmd.Parameters.AddWithValue("@burger", detail.BurgerId.HasValue ? detail.BurgerId : DBNull.Value);
        cmd.Parameters.AddWithValue("@menu", detail.MenuId.HasValue ? detail.MenuId : DBNull.Value);

        cmd.Parameters.AddWithValue("@qte", detail.Quantite);
        cmd.Parameters.AddWithValue("@prix", detail.PrixUnitaire);

        detail.Id = (int)await cmd.ExecuteScalarAsync();
        return detail;
    }

    public async Task<List<CommandeDetail>> GetByCommandeIdAsync(int commandeId)
    {
        using var conn = new NpgsqlConnection(_connectionString);
        await conn.OpenAsync();

        const string sql = "SELECT * FROM commande_detail WHERE commande_id = @id";

        using var cmd = new NpgsqlCommand(sql, conn);
        cmd.Parameters.AddWithValue("@id", commandeId);

        using var reader = await cmd.ExecuteReaderAsync();

        List<CommandeDetail> list = new();

        while (await reader.ReadAsync())
        {
            list.Add(new CommandeDetail
            {
                Id = reader.GetInt32(reader.GetOrdinal("id")),
                CommandeId = commandeId,
                BurgerId = reader.IsDBNull(reader.GetOrdinal("burger_id")) ? null : reader.GetInt32(reader.GetOrdinal("burger_id")),
                MenuId = reader.IsDBNull(reader.GetOrdinal("menu_id")) ? null : reader.GetInt32(reader.GetOrdinal("menu_id")),
                Quantite = reader.GetInt32(reader.GetOrdinal("quantite")),
                PrixUnitaire = reader.GetDecimal(reader.GetOrdinal("prix_unitaire"))
            });
        }

        return list;
    }
}