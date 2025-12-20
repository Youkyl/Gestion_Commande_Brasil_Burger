namespace BrasilBurgerApi.DTO;

public class LoginResponse
{
    public string Token { get; set; }
    public string FullName { get; set; }
    public int ClientId { get; set; }
}