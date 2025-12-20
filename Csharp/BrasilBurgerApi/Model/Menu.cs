using System.Collections.Generic;

namespace BrasilBurgerApi.Model
{
public class Menu
{
    public int Id { get; set; }
    public string Nom { get; set; } = null!;
    public string? ImageUrl { get; set; }
    public bool IsArchive { get; set; }
    public decimal? Prix { get; set; }
    public int? BurgerId { get; set; }
    public DateTime DateCreation { get; set; }
}

}
