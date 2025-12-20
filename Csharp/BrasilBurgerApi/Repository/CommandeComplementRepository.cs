using BrasilBurgerApi.Model;
using Npgsql;

namespace BrasilBurgerApi.Repository;

public class CommandeComplementRepository : ICommandeComplementRepository
{
    private readonly string _connectionString;

    public CommandeComplementRepository(string connectionString)
    {
        _connectionString = connectionString;
    }

    public async Task<CommandeComplement> AddComplementAsync(CommandeComplement comp)
    {
        using var conn = new NpgsqlConnection(_connectionString);
        await conn.OpenAsync();

        const string sql = @"
            INSERT INTO commande_complement 
            (commande_detail_id, complement_id, quantite, prix_unitaire)
            VALUES (@detail, @comp, @qte, @prix)
            RETURNING id;
        ";

        using var cmd = new NpgsqlCommand(sql, conn);

        cmd.Parameters.AddWithValue("@detail", comp.CommandeDetailId);
        cmd.Parameters.AddWithValue("@comp", comp.ComplementId);
        cmd.Parameters.AddWithValue("@qte", comp.Quantite);
        cmd.Parameters.AddWithValue("@prix", comp.PrixUnitaire);

        comp.Id = (int)await cmd.ExecuteScalarAsync();
        return comp;
    }
}