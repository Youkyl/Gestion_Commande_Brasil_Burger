namespace BrasilBurgerApi.DTO.Commande
{
    public class CreateCommandeDetailDto
    {
        public int? BurgerId { get; set; }
        public int? MenuId { get; set; }
        public int Quantite { get; set; }
        public List<CreateCommandeComplementDto> Complements { get; set; } = new();
    }
}
