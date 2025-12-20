using BrasilBurgerApi.Repository;
using Microsoft.AspNetCore.Mvc;

namespace BrasilBurgerApi.Controllers;

[ApiController]
[Route("api/complements")]
public class ComplementController : ControllerBase
{
    private readonly IComplementRepository _repo;

    public ComplementController(IComplementRepository repo)
    {
        _repo = repo;
    }

    [HttpGet]
    public async Task<IActionResult> GetAll()
    {
        return Ok(await _repo.GetAllAsync());
    }

    [HttpGet("{id}")]
    public async Task<IActionResult> GetById(int id)
    {
        var c = await _repo.GetByIdAsync(id);
        if (c == null) return NotFound("Complément introuvable.");
        return Ok(c);
    }

    [HttpGet("detail/{detailId}")]
    public async Task<IActionResult> GetByDetailId(int detailId)
    {
        var list = await _repo.GetByDetailIdAsync(detailId);
        return Ok(list);
    }
}