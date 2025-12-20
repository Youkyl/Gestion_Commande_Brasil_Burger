using BrasilBurgerApi.Repository;
using BrasilBurgerApi.Config;
using BrasilBurgerApi.Service;
using Microsoft.Extensions.Options;

var builder = WebApplication.CreateBuilder(args);

// Controllers
builder.Services.AddControllers();

// Bind DatabaseSettings
builder.Services.Configure<DatabaseSettings>(
    builder.Configuration.GetSection("ConnectionStrings")
);

// Inject Repository
builder.Services.AddScoped<IClientRepository>(sp =>
{
    var dbSettings = sp.GetRequiredService<IOptions<DatabaseSettings>>().Value;
    return new ClientRepository(dbSettings.NeonDB);
});

// Inject Services
builder.Services.AddScoped<ClientService>();

// Swagger
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

var app = builder.Build();

// Swagger middlewares
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseHttpsRedirection();
app.MapControllers();
app.Run();
