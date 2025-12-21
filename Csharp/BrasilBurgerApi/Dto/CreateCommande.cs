using BrasilBurgerApi.DTO.Commande;

namespace BrasilBurgerApi.DTO;

public class CreateCommande
{
    public string TypeCommande { get; set; }      
    public int ClientId { get; set; }
    public int? ZoneId { get; set; }             

    public List<CommandeBurger> Burgers { get; set; } = new();
    public List<CommandeMenu> Menus { get; set; } = new();

    public List<CreateCommandeDetailDto> Details { get; set; } = new();



}