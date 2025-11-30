# E-Commerce API - Backend Challenge 2

API REST para gerenciamento de produtos e sistema de carrinho de compras desenvolvida com Java Spring Boot, PostgreSQL e RabbitMQ.

## 🚀 Tecnologias

- **Java 17**
- **Spring Boot 3.5.5**
- **PostgreSQL 15**
- **RabbitMQ 3.12**
- **Docker & Docker Compose**
- **Flyway** (Migração de banco de dados)
- **Lombok**
- **ModelMapper**
- **Swagger/OpenAPI** (Documentação)

## 📋 Funcionalidades

### Produtos (Products)
- ✅ **GET** `/products` - Listar todos os produtos
- ✅ **GET** `/products/{id}` - Buscar produto por ID
- ✅ **POST** `/products` - Criar novo produto
- ✅ **PUT** `/products/{id}` - Atualizar produto
- ✅ **DELETE** `/products/{id}` - Deletar produto

### Carrinho (Cart)
- ✅ **POST** `/cart/checkout` - Realizar checkout do carrinho
- ✅ **GET** `/cart/orders` - Listar todos os pedidos
- ✅ **GET** `/cart/orders/{id}` - Buscar pedido por ID

## 🎯 Regras de Negócio

1. ✅ Produto não pode ser cadastrado com valor negativo
2. ✅ Ao fazer checkout, o estoque é automaticamente diminuído
3. ✅ Se o estoque for insuficiente, a venda é bloqueada

## 🗄️ Estrutura do Banco de Dados

### Tabela: `products`
| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | BIGSERIAL | Chave primária |
| name | VARCHAR(255) | Nome do produto |
| price | DECIMAL(10,2) | Preço (não negativo) |
| stock | INTEGER | Quantidade em estoque |

### Tabela: `orders`
| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | BIGSERIAL | Chave primária |
| total | DECIMAL(10,2) | Valor total do pedido |
| created_at | TIMESTAMP | Data de criação |

### Tabela: `order_items`
| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | BIGSERIAL | Chave primária |
| order_id | BIGINT | FK para orders |
| product_id | BIGINT | FK para products |
| quantity | INTEGER | Quantidade do produto |
| price | DECIMAL(10,2) | Preço no momento da compra |

## 🛠️ Configuração e Execução

### Pré-requisitos
- Java 17+
- Maven
- Docker & Docker Compose

### 1. Subir os serviços (PostgreSQL e RabbitMQ)
```bash
docker-compose up -d
```

### 2. Compilar o projeto
```bash
mvn clean install
```

### 3. Executar a aplicação
```bash
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

## 📚 Documentação da API (Swagger)

Após iniciar a aplicação, acesse:
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

## 🔌 Acessar Serviços

### PostgreSQL
- **Host**: localhost
- **Porta**: 5432
- **Database**: modelo_db
- **Usuário**: postgres
- **Senha**: 123456

### RabbitMQ Management
- **URL**: http://localhost:15672
- **Usuário**: admin
- **Senha**: admin123

## 📝 Exemplo de Requisições

### Criar Produto
```bash
POST /products
Content-Type: application/json

{
  "name": "Notebook Dell",
  "price": 3499.99,
  "stock": 10
}
```

### Realizar Checkout
```bash
POST /cart/checkout
Content-Type: application/json

{
  "items": [
    {
      "productId": 1,
      "quantity": 2
    },
    {
      "productId": 2,
      "quantity": 1
    }
  ]
}
```

## 🏗️ Estrutura do Projeto

```
src/main/java/br/com/thallysprojetos/boilerplate/
├── configs/          # Configurações (Flyway, Mapper, Swagger)
├── controllers/      # Endpoints REST
├── dtos/             # Data Transfer Objects
├── exceptions/       # Tratamento de exceções
├── mappers/          # Conversão Entity <-> DTO
├── models/           # Entidades JPA
├── repositories/     # Repositórios JPA
└── services/         # Lógica de negócio
```

## 🧪 Dados de Teste

A aplicação já vem com 10 produtos pré-cadastrados após a inicialização:
- Notebook Dell Inspiron
- Mouse Logitech MX Master
- Teclado Mecânico Keychron
- Monitor LG 27 Polegadas
- Webcam Logitech C920
- Headset HyperX Cloud II
- SSD Samsung 1TB
- Mousepad Gamer Grande
- Hub USB-C 7 em 1
- Cadeira Gamer DT3 Sports

## 📦 Migrations (Flyway)

As migrations são executadas automaticamente ao iniciar a aplicação:
- `V1__scriptInicialized.sql` - Inicialização
- `V2__scriptDropTables.sql` - Limpeza de tabelas antigas
- `V3__scriptCreateTables.sql` - Criação das tabelas
- `V4__scriptInsert_data.sql` - Inserção de dados de teste

## 🐛 Tratamento de Erros

A API retorna mensagens de erro apropriadas:
- **400 Bad Request**: Validação de dados inválidos
- **404 Not Found**: Recurso não encontrado
- **409 Conflict**: Conflito de dados
- **500 Internal Server Error**: Erro interno do servidor

## 👨‍💻 Autor

**Grupo Moura - Desafio 2**

---

**Nota**: Este projeto foi desenvolvido como parte do Desafio 2 do Grupo Moura.
