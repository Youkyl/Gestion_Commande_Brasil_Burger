using BrasilBurgerApi.Repository;
using Microsoft.AspNetCore.Mvc;

namespace BrasilBurgerApi.Controllers;

[ApiController]
[Route("api/burgers")]
public class BurgerController : ControllerBase
{
    private readonly IBurgerRepository _repo;

    public BurgerController(IBurgerRepository repo)
    {
        _repo = repo;
    }

    [HttpGet]
    public async Task<IActionResult> GetAll()
    {
        var list = await _repo.GetAllAsync();
        return Ok(list);
    }

    [HttpGet("active")]
    public async Task<IActionResult> GetActive()
    {
        var list = await _repo.GetActiveAsync();
        return Ok(list);
    }

    [HttpGet("{id}")]
    public async Task<IActionResult> GetById(int id)
    {
        var burger = await _repo.GetByIdAsync(id);
        if (burger == null) return NotFound("Burger introuvable.");
        return Ok(burger);
    }
}