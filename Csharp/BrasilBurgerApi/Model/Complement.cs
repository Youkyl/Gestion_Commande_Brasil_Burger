using BrasilBurgerApi.Model;

namespace BrasilBurgerApi.Model
{

public class Complement
{
    public int Id { get; set; }
    public string Nom { get; set; } = null!;
    public decimal Prix { get; set; }
    public string? ImageUrl { get; set; }
    public bool IsArchive { get; set; }

    public string Type { get; set; } = null!;  
    public int Stock { get; set; }

    public int? RelationQuantite { get; set; }
    public decimal? RelationPrixUnitaire { get; set; }
}

}
