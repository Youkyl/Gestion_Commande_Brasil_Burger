namespace BrasilBurgerApi.DTO;

public class CommandeBurger
{
    public int BurgerId { get; set; }
    public int Quantite { get; set; }
    public List<CommandeComplementDto> Complements { get; set; } = new();
}