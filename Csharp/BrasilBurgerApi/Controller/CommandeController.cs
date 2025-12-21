using BrasilBurgerApi.DTO;
using BrasilBurgerApi.Service;
using Microsoft.AspNetCore.Mvc;

namespace BrasilBurgerApi.Controllers;

[ApiController]
[Route("api/commandes")]
public class CommandeController : ControllerBase
{
    private readonly CommandeService _service;

    public CommandeController(CommandeService service)
    {
        _service = service;
    }

    [HttpPost]
    public async Task<IActionResult> Create([FromBody] CreateCommande dto)
    {
        var id = await _service.CreateAsync(dto);
        return Ok(new { CommandeId = id, Message = "Commande créée avec succès" });
    }



}