using BrasilBurgerApi.Repository;
using BrasilBurgerApi.Service;

var builder = WebApplication.CreateBuilder(args);

// Controllers
builder.Services.AddControllers();

// Repository
builder.Services.AddScoped<IClientRepository>(sp =>
    new ClientRepository(builder.Configuration.GetConnectionString("NeonDB")!)
);

builder.Services.AddScoped<IBurgerRepository>(sp =>
    new BurgerRepository(builder.Configuration.GetConnectionString("NeonDB")!));

builder.Services.AddScoped<IMenuRepository>(sp =>
    new MenuRepository(builder.Configuration.GetConnectionString("NeonDB")!));

builder.Services.AddScoped<IComplementRepository>(sp =>
    new ComplementRepository(builder.Configuration.GetConnectionString("NeonDB")!));

builder.Services.AddScoped<ICommandeRepository>(sp =>
    new CommandeRepository(builder.Configuration.GetConnectionString("NeonDB")!));

builder.Services.AddScoped<ICommandeDetailRepository>(sp =>
    new CommandeDetailRepository(builder.Configuration.GetConnectionString("NeonDB")!));

builder.Services.AddScoped<ICommandeComplementRepository>(sp =>
    new CommandeComplementRepository(builder.Configuration.GetConnectionString("NeonDB")!));







// Services
builder.Services.AddScoped<ClientService>();
builder.Services.AddScoped<CatalogueService>();

builder.Services.AddScoped<MenuService>();
builder.Services.AddScoped<ComplementService>();




// Swagger
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

var app = builder.Build();

if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseHttpsRedirection();

app.MapControllers();

app.Run();