using BrasilBurgerApi.Model;
using Npgsql;

namespace BrasilBurgerApi.Repository;

public class CommandeRepository : ICommandeRepository
{
    private readonly string _connectionString;

    public CommandeRepository(string connectionString)
    {
        _connectionString = connectionString;
    }

    public async Task<Commande> CreateCommandeAsync(Commande commande)
    {
        using var conn = new NpgsqlConnection(_connectionString);
        await conn.OpenAsync();

        const string sql = @"
            INSERT INTO commande (reference, type_commande, montant_total, client_id, zone_id)
            VALUES (@ref, @type, @total, @client, @zone)
            RETURNING id, date_commande, etat;
        ";

        using var cmd = new NpgsqlCommand(sql, conn);

        cmd.Parameters.AddWithValue("@ref", commande.Reference);
        cmd.Parameters.AddWithValue("@type", commande.TypeCommande);
        cmd.Parameters.AddWithValue("@total", commande.MontantTotal);
        cmd.Parameters.AddWithValue("@client", commande.ClientId);

        if (commande.ZoneId == null)
            cmd.Parameters.AddWithValue("@zone", DBNull.Value);
        else
            cmd.Parameters.AddWithValue("@zone", commande.ZoneId);

        using var reader = await cmd.ExecuteReaderAsync();
        if (await reader.ReadAsync())
        {
            commande.Id = reader.GetInt32(0);
            commande.DateCommande = reader.GetDateTime(1);
            commande.Etat = reader.GetString(2);
        }

        return commande;
    }

    public async Task<Commande?> GetCommandeByReferenceAsync(string reference)
    {
        using var conn = new NpgsqlConnection(_connectionString);
        await conn.OpenAsync();

        const string sql = "SELECT * FROM commande WHERE reference = @ref LIMIT 1;";

        using var cmd = new NpgsqlCommand(sql, conn);
        cmd.Parameters.AddWithValue("@ref", reference);

        using var reader = await cmd.ExecuteReaderAsync();

        if (await reader.ReadAsync())
        {
            return new Commande
            {
                Id = reader.GetInt32(reader.GetOrdinal("id")),
                Reference = reader.GetString(reader.GetOrdinal("reference")),
                DateCommande = reader.GetDateTime(reader.GetOrdinal("date_commande")),
                TypeCommande = reader.GetString(reader.GetOrdinal("type_commande")),
                Etat = reader.GetString(reader.GetOrdinal("etat")),
                MontantTotal = reader.GetDecimal(reader.GetOrdinal("montant_total")),
                ClientId = reader.GetInt32(reader.GetOrdinal("client_id")),
                ZoneId = reader.IsDBNull(reader.GetOrdinal("zone_id")) ? null : reader.GetInt32(reader.GetOrdinal("zone_id"))
            };
        }

        return null;
    }

    public async Task UpdateMontantTotalAsync(int commandeId, decimal montantTotal)
    {
        using var conn = new NpgsqlConnection(_connectionString);
        await conn.OpenAsync();

        const string sql = "UPDATE commande SET montant_total = @total WHERE id = @id;";

        using var cmd = new NpgsqlCommand(sql, conn);

        cmd.Parameters.AddWithValue("@total", montantTotal);
        cmd.Parameters.AddWithValue("@id", commandeId);

        await cmd.ExecuteNonQueryAsync();
    }


    public async Task<int> CreateDetailAsync(CommandeDetail detail)
    {
        using var con = new NpgsqlConnection(_connectionString);
        await con.OpenAsync();

        const string sql = @"
            INSERT INTO commande_detail (commande_id, burger_id, menu_id, quantite, prix_unitaire)
            VALUES (@cmd, @burger, @menu, @qte, @prix)
            RETURNING id;
        ";

        using var cmd = new NpgsqlCommand(sql, con);

        cmd.Parameters.AddWithValue("@cmd", detail.CommandeId);
        cmd.Parameters.AddWithValue("@burger", (object?)detail.BurgerId ?? DBNull.Value);
        cmd.Parameters.AddWithValue("@menu", (object?)detail.MenuId ?? DBNull.Value);
        cmd.Parameters.AddWithValue("@qte", detail.Quantite);
        cmd.Parameters.AddWithValue("@prix", detail.PrixUnitaire);

        return (int)(await cmd.ExecuteScalarAsync())!;
    }

    public async Task CreateComplementAsync(CommandeComplement comp)
    {
        using var con = new NpgsqlConnection(_connectionString);
        await con.OpenAsync();

        const string sql = @"
            INSERT INTO commande_complement (commande_detail_id, complement_id, quantite, prix_unitaire)
            VALUES (@detail, @comp, @qte, @prix)
        ";

        using var cmd = new NpgsqlCommand(sql, con);
        cmd.Parameters.AddWithValue("@detail", comp.CommandeDetailId);
        cmd.Parameters.AddWithValue("@comp", comp.ComplementId);
        cmd.Parameters.AddWithValue("@qte", comp.Quantite);
        cmd.Parameters.AddWithValue("@prix", comp.PrixUnitaire);

        await cmd.ExecuteNonQueryAsync();
    }

     public async Task<string> GenerateReferenceAsync()
    {
        return $"CMD-{Guid.NewGuid().ToString().Substring(0, 8).ToUpper()}";
    }

    public async Task<int> AddDetailAsync(CommandeDetail detail)
    {
        using var connection = new NpgsqlConnection(_connectionString);
        await connection.OpenAsync();

        var query = @"
            INSERT INTO commande_detail (commande_id, burger_id, menu_id, quantite, prix_unitaire)
            VALUES (@cmd, @burger, @menu, @qte, @prix)
            RETURNING id;
        ";

        using var cmd = new NpgsqlCommand(query, connection);

        cmd.Parameters.AddWithValue("@cmd", detail.CommandeId);
        cmd.Parameters.AddWithValue("@burger", (object?)detail.BurgerId ?? DBNull.Value);
        cmd.Parameters.AddWithValue("@menu", (object?)detail.MenuId ?? DBNull.Value);
        cmd.Parameters.AddWithValue("@qte", detail.Quantite);
        cmd.Parameters.AddWithValue("@prix", detail.PrixUnitaire);

        var id = (int)(await cmd.ExecuteScalarAsync())!;
        return id;
    }

    public async Task AddComplementAsync(CommandeComplement complement)
    {
        using var connection = new NpgsqlConnection(_connectionString);
        await connection.OpenAsync();

        var query = @"
            INSERT INTO commande_complement (commande_detail_id, complement_id, quantite, prix_unitaire)
            VALUES (@detail, @comp, @qte, @prix);
        ";

        using var cmd = new NpgsqlCommand(query, connection);

        cmd.Parameters.AddWithValue("@detail", complement.CommandeDetailId);
        cmd.Parameters.AddWithValue("@comp", complement.ComplementId);
        cmd.Parameters.AddWithValue("@qte", complement.Quantite);
        cmd.Parameters.AddWithValue("@prix", complement.PrixUnitaire);

        await cmd.ExecuteNonQueryAsync();
    }

    public async Task<decimal> GetBurgerPriceAsync(int burgerId)
    {
        using var connection = new NpgsqlConnection(_connectionString);
        await connection.OpenAsync();

        const string query = @"SELECT prix FROM burger WHERE id = @id";

        using var cmd = new NpgsqlCommand(query, connection);
        cmd.Parameters.AddWithValue("@id", burgerId);

        var result = await cmd.ExecuteScalarAsync();
        return result == null ? 0 : (decimal)result;
    }

    public async Task<decimal> GetMenuPriceAsync(int menuId)
    {
        using var connection = new NpgsqlConnection(_connectionString);
        await connection.OpenAsync();

        const string query = @"SELECT prix FROM menu WHERE id = @id";

        using var cmd = new NpgsqlCommand(query, connection);
        cmd.Parameters.AddWithValue("@id", menuId);

        var result = await cmd.ExecuteScalarAsync();
        return result == null ? 0 : (decimal)result;
    }

    public async Task<decimal> GetComplementPriceAsync(int complementId)
    {
        using var connection = new NpgsqlConnection(_connectionString);
        await connection.OpenAsync();

        const string query = @"SELECT prix FROM complement WHERE id = @id";

        using var cmd = new NpgsqlCommand(query, connection);
        cmd.Parameters.AddWithValue("@id", complementId);

        var result = await cmd.ExecuteScalarAsync();
        return result == null ? 0 : (decimal)result;
    }
}