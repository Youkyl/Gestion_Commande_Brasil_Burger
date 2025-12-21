

using BrasilBurgerApi.Model;

namespace BrasilBurgerApi.Model
{
    public class Commande
    {
        public int Id { get; set; }
        public string Reference { get; set; } = string.Empty;
        public DateTime DateCommande { get; set; }
        public string TypeCommande { get; set; } = string.Empty;    
        public string Etat { get; set; } = string.Empty;           
        public decimal MontantTotal { get; set; }
        public int ClientId { get; set; }
        public int? ZoneId { get; set; }

        // Navigation
        public List<CommandeDetail> Details { get; set; } = new();

        public static implicit operator int(Commande v)
        {
            throw new NotImplementedException();
        }
    }

}
