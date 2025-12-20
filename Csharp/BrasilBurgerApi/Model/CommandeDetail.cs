namespace BrasilBurgerApi.Model
{
    public class CommandeDetail
    {
        public int Id { get; set; }
        public int CommandeId { get; set; }
        public int? BurgerId { get; set; }
        public int? MenuId { get; set; }
        public int Quantite { get; set; }
        public decimal PrixUnitaire { get; set; }

        // Navigation
        public List<CommandeComplement> Complements { get; set; } = new();
    }


}
