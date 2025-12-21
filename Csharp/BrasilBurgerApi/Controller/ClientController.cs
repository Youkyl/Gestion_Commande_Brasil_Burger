
using BrasilBurgerApi.DTO;
using BrasilBurgerApi.Service;
using Microsoft.AspNetCore.Mvc;

namespace BrasilBurgerApi.Controllers;

[ApiController]
[Route("api/client")]
public class ClientController : ControllerBase
{
    private readonly ClientService _service;

    public ClientController(ClientService service)
    {
        _service = service;
    }

    [HttpPost("register")]
    public async Task<IActionResult> Register(RegisterClientDto dto)
    {
        try
        {
            var result = await _service.RegisterAsync(dto);
            return Ok(result);
        }
        catch (Exception ex)
        {
            return BadRequest(new { message = ex.Message });
        }
    }

    [HttpPost("login")]
    public async Task<IActionResult> Login(LoginRequest dto)
    {
        var result = await _service.LoginAsync(dto);

        if (result == null)
            return Unauthorized(new { message = "Email ou mot de passe incorrect." });

        return Ok(result);
    }
}