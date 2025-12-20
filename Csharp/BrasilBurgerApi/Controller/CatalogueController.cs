using BrasilBurgerApi.Service;
using Microsoft.AspNetCore.Mvc;

namespace BrasilBurgerApi.Controllers;

[ApiController]
[Route("api/catalogue")]
public class CatalogueController : ControllerBase
{
    private readonly CatalogueService _service;

    public CatalogueController(CatalogueService service)
    {
        _service = service;
    }

    [HttpGet("burgers")]
    public async Task<IActionResult> GetBurgers()
        => Ok(await _service.GetBurgersAsync());

    [HttpGet("menus")]
    public async Task<IActionResult> GetMenus()
        => Ok(await _service.GetMenusAsync());

    [HttpGet("complements")]
    public async Task<IActionResult> GetComplements()
        => Ok(await _service.GetComplementsAsync());
}