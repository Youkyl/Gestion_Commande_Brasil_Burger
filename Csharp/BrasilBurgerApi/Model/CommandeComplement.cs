namespace BrasilBurgerApi.Model
{
    public class CommandeComplement
    {
        public int Id { get; set; }
        public int CommandeDetailId { get; set; }
        public int ComplementId { get; set; }
        public int Quantite { get; set; }
        public decimal PrixUnitaire { get; set; }
    }

}