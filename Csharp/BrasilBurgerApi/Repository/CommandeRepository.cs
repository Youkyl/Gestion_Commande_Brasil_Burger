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
}