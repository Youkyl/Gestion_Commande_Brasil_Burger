namespace BrasilBurgerApi.DTO;

public class RegisterClientDto
{
    public string Nom { get; set; } = "";
    public string Prenom { get; set; } = "";
    public string Telephone { get; set; } = "";
    public string Email { get; set; } = "";
    public string Password { get; set; } = "";
    public string Adresse { get; set; } = "";
}