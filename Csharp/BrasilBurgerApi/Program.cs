using BrasilBurgerApi.Repository;
using BrasilBurgerApi.Service;

var builder = WebApplication.CreateBuilder(args);

// Controllers
builder.Services.AddControllers();

// Repository
builder.Services.AddScoped<IClientRepository>(sp =>
    new ClientRepository(builder.Configuration.GetConnectionString("NeonDB")!)
);

// Services
builder.Services.AddScoped<ClientService>();

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