

namespace BrasilBurgerApi.Model
{
public class Burger
{
    public int Id { get; set; }
    public string Nom { get; set; } = null!;
    public string? Ingredient { get; set; }
    public decimal Prix { get; set; }
    public string? ImageUrl { get; set; }
    public bool IsArchive { get; set; }
    public DateTime DateCreation { get; set; }
}

}
