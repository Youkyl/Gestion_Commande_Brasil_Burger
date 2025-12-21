using BrasilBurgerApi.Model;

namespace BrasilBurgerApi.DTO;

public class CommandeMenu
{
    public int MenuId { get; set; }
    public int Quantite { get; set; }
    public List<CommandeComplement> Complements { get; set; } = new();
}