using BrasilBurgerApi.DTO;
using BrasilBurgerApi.DTO.Commande;
using BrasilBurgerApi.Model;
using BrasilBurgerApi.Repository;

namespace BrasilBurgerApi.Service;

public class CommandeService
{
    private readonly ICommandeRepository _commandeRepo;
    private readonly ICommandeDetailRepository _detailRepo;
    private readonly ICommandeComplementRepository _complementRepo;
    private readonly IBurgerRepository _burgerRepo;
    private readonly IMenuRepository _menuRepo;
    private readonly IComplementRepository _complRepo;

    public CommandeService(
        ICommandeRepository commandeRepo,
        ICommandeDetailRepository detailRepo,
        ICommandeComplementRepository complementRepo,
        IBurgerRepository burgerRepo,
        IMenuRepository menuRepo,
        IComplementRepository complRepo
    )
    {
        _commandeRepo = commandeRepo;
        _detailRepo = detailRepo;
        _burgerRepo = burgerRepo;
        _menuRepo = menuRepo;
        _complRepo = complRepo;
    }


    private string GenerateReference()
    {
        return $"CMD-{DateTime.Now:yyyyMMddHHmmss}-{Guid.NewGuid().ToString().Substring(0, 5)}";
    }



    public async Task<Commande> CreateCommandeAsync(int clientId, string typeCommande, int? zoneId)
    {
        var commande = new Commande
        {
            Reference = GenerateReference(),
            ClientId = clientId,
            TypeCommande = typeCommande,
            ZoneId = zoneId,
            MontantTotal = 0
        };

        return await _commandeRepo.CreateCommandeAsync(commande);
    }



    public async Task<CommandeDetail> AddDetailAsync(int commandeId, int? burgerId, int? menuId, int quantite)
    {
        decimal prixUnitaire = 0;

        if (burgerId.HasValue)
        {
            var burger = await _burgerRepo.GetByIdAsync(burgerId.Value);
            if (burger == null) throw new Exception("Burger introuvable.");
            prixUnitaire = burger.Prix;
        }

        if (menuId.HasValue)
        {
            var menu = await _menuRepo.GetByIdAsync(menuId.Value);
            if (menu == null) throw new Exception("Menu introuvable.");
            prixUnitaire = (decimal)menu.Prix;
        }

        var detail = new CommandeDetail
        {
            CommandeId = commandeId,
            BurgerId = burgerId,
            MenuId = menuId,
            Quantite = quantite,
            PrixUnitaire = prixUnitaire
        };

        var savedDetail = await _detailRepo.AddDetailAsync(detail);

        await RecalculateTotal(commandeId);

        return savedDetail;
    }




    public async Task<CommandeComplement> AddComplementAsync(int detailId, int complementId, int quantite)
    {
        var comp = await _complRepo.GetByIdAsync(complementId);
        if (comp == null) throw new Exception("Complément introuvable.");

        var detailComp = new CommandeComplement
        {
            CommandeDetailId = detailId,
            ComplementId = complementId,
            Quantite = quantite,
            PrixUnitaire = comp.Prix
        };

        var saved = await _complementRepo.AddComplementAsync(detailComp);


        var detail = await _detailRepo.GetByCommandeIdAsync(detailId);
        await RecalculateTotal(detail.First().CommandeId);

        return saved;
    }



    public async Task RecalculateTotal(int commandeId)
    {
        var details = await _detailRepo.GetByCommandeIdAsync(commandeId);

        decimal total = 0;

        foreach (var d in details)
        {
            total += d.PrixUnitaire * d.Quantite;


            var complements = await _complRepo.GetByDetailIdAsync(d.Id);

            foreach (var comp in complements)
            {
                total += comp.RelationPrixUnitaire.GetValueOrDefault() * comp.RelationQuantite.GetValueOrDefault();
            }
        }

        await _commandeRepo.UpdateMontantTotalAsync(commandeId, total);
    }



    public async Task<Commande?> GetCommandeByReference(string reference)
    {
        return await _commandeRepo.GetCommandeByReferenceAsync(reference);
    }


     public async Task<int> CreateAsync(CreateCommande dto)
    {
        decimal montantTotal = 0;


        string reference = $"CMD-{DateTime.UtcNow.Ticks}";

        var commande = new Commande
        {
            Reference = reference,
            TypeCommande = dto.TypeCommande,
            ClientId = dto.ClientId,
            ZoneId = dto.ZoneId,
            MontantTotal = 0
        };

        int commandeId = await _commandeRepo.CreateCommandeAsync(commande);


        foreach (var item in dto.Burgers)
        {
            var burger = await _burgerRepo.GetByIdAsync(item.BurgerId);
            var prix = burger.Prix;

            montantTotal += prix * item.Quantite;

            var detailId = await _commandeRepo.CreateDetailAsync(new CommandeDetail
            {
                CommandeId = commandeId,
                BurgerId = burger.Id,
                Quantite = item.Quantite,
                PrixUnitaire = prix
            });


            foreach (var comp in item.Complements)
            {
                var complement = await _complRepo.GetByIdAsync(comp.ComplementId);
                montantTotal += complement.Prix * comp.Quantite;

                await _commandeRepo.CreateComplementAsync(new CommandeComplement
                {
                    CommandeDetailId = detailId,
                    ComplementId = comp.ComplementId,
                    Quantite = comp.Quantite,
                    PrixUnitaire = complement.Prix
                });
            }
        }


        foreach (var item in dto.Menus)
        {
            var menu = await _menuRepo.GetByIdAsync(item.MenuId);
            var prix = menu.Prix;

            montantTotal += (decimal)(prix * item.Quantite);

            var detailId = await _commandeRepo.CreateDetailAsync(new CommandeDetail
            {
                CommandeId = commandeId,
                MenuId = menu.Id,
                Quantite = item.Quantite,
                PrixUnitaire = (decimal)prix
            });
            

            foreach (var comp in item.Complements)
            {
                var complement = await _complRepo.GetByIdAsync(comp.ComplementId);
                montantTotal += complement.Prix * comp.Quantite;

                await _commandeRepo.CreateComplementAsync(new CommandeComplement
                {
                    CommandeDetailId = detailId,
                    ComplementId = comp.ComplementId,
                    Quantite = comp.Quantite,
                    PrixUnitaire = complement.Prix
                });
            }
        }


        commande.Id = commandeId;
        commande.MontantTotal = montantTotal;

        return commandeId;
    }




}