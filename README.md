# 🛒 Sistema de Produtos e Carrinho de Compras

Um pequeno sistema de produtos com carrinho de compras e cálculo automático de total. Aplicação full-stack moderna que permite gerenciar produtos, adicionar itens ao carrinho e realizar compras com controle de estoque em tempo real.

![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.5-brightgreen?style=flat-square&logo=spring)
![Angular](https://img.shields.io/badge/Angular-21-red?style=flat-square&logo=angular)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?style=flat-square&logo=postgresql)
![Material Design](https://img.shields.io/badge/Material%20Design-UI-purple?style=flat-square&logo=material-design)

## 📋 Índice

- [Sobre o Projeto](#-sobre-o-projeto)
- [Funcionalidades](#-funcionalidades)
- [Tecnologias](#-tecnologias)
- [Arquitetura](#-arquitetura)
- [Pré-requisitos](#-pré-requisitos)
- [Instalação e Configuração](#-instalação-e-configuração)
- [Executando o Projeto](#-executando-o-projeto)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [API Endpoints](#-api-endpoints)
- [Demonstração](#-demonstração)
- [Testes](#-testes)
- [Contribuindo](#-contribuindo)

## 📖 Sobre o Projeto

Este projeto é uma aplicação completa de e-commerce simplificada que demonstra a integração entre um backend robusto em Spring Boot e um frontend moderno em Angular. O sistema permite:

- **Gerenciamento de Produtos**: CRUD completo com validações
- **Carrinho de Compras**: Adicionar, remover e ajustar quantidades
- **Controle de Estoque**: Validação automática e atualização em tempo real
- **Finalização de Pedidos**: Checkout com cálculo automático de totais
- **Interface Responsiva**: Design moderno com Material Design

## ✨ Funcionalidades

### 🎯 Gerenciamento de Produtos
- ✅ Listar todos os produtos com paginação
- ✅ Adicionar novos produtos
- ✅ Editar produtos existentes
- ✅ Excluir produtos
- ✅ Visualizar detalhes expandidos dos produtos
- ✅ Validação de dados (preço positivo, estoque não negativo)

### 🛍️ Carrinho de Compras
- ✅ Adicionar produtos ao carrinho
- ✅ Ajustar quantidades (aumentar/diminuir)
- ✅ Remover itens do carrinho
- ✅ Cálculo automático de subtotais e total geral
- ✅ Visualização em painel lateral deslizante
- ✅ Badge com contador de itens

### 💳 Finalização de Compra
- ✅ Checkout com validação de estoque
- ✅ Atualização automática de estoque após compra
- ✅ Geração de pedido com ID único
- ✅ Notificações visuais de sucesso/erro
- ✅ Limpeza automática do carrinho após compra

### 🎨 Interface do Usuário
- ✅ Design responsivo e moderno
- ✅ Animações suaves e intuitivas
- ✅ Notificações customizadas (sem alerts do navegador)
- ✅ Indicadores de loading durante operações
- ✅ Feedback visual em todas as ações

## 🚀 Tecnologias

### Backend

| Tecnologia | Versão | Descrição |
|------------|--------|-----------|
| **Java** | 17 | Linguagem de programação principal |
| **Spring Boot** | 3.5.5 | Framework para criação de APIs REST |
| **Spring Data JPA** | 3.5.5 | Persistência e mapeamento objeto-relacional |
| **Spring Validation** | 3.5.5 | Validação de dados com Bean Validation |
| **PostgreSQL** | 16+ | Banco de dados relacional |
| **Flyway** | Latest | Controle de versionamento do banco de dados |
| **Lombok** | Latest | Redução de boilerplate code |
| **ModelMapper** | 3.2.4 | Mapeamento entre DTOs e Entities |
| **SpringDoc OpenAPI** | 2.8.13 | Documentação Swagger/OpenAPI |
| **Maven** | 3.8+ | Gerenciamento de dependências |

### Frontend

| Tecnologia | Versão | Descrição |
|------------|--------|-----------|
| **Angular** | 21.0.0 | Framework SPA |
| **Angular Material** | 21.0.1 | Biblioteca de componentes UI |
| **TypeScript** | 5.9.2 | Superset JavaScript com tipagem |
| **RxJS** | 7.8.0 | Programação reativa |
| **Angular Forms** | 21.0.0 | Formulários reativos |
| **Angular Router** | 21.0.0 | Roteamento SPA |
| **Angular CDK** | 21.0.1 | Kit de desenvolvimento de componentes |
| **NPM** | 11.6.2 | Gerenciador de pacotes |

## 🏗️ Arquitetura

### Arquitetura Geral

```
┌─────────────────────────────────────────────────────────┐
│                    FRONTEND (Angular)                   │
│  ┌────────────┐  ┌────────────┐  ┌────────────────┐   │
│  │ Components │  │  Services  │  │     Models     │   │
│  │            │  │            │  │                │   │
│  │ - Product  │  │ - Product  │  │ - Product      │   │
│  │ - Cart     │  │ - Order    │  │ - CartItem     │   │
│  │ - Modal    │  │ - HTTP     │  │ - Order        │   │
│  └────────────┘  └────────────┘  └────────────────┘   │
└────────────────────┬────────────────────────────────────┘
                     │ HTTP/REST (JSON)
                     │ Port: 4200 → 8080
┌────────────────────┴────────────────────────────────────┐
│                    BACKEND (Spring Boot)                │
│  ┌────────────┐  ┌────────────┐  ┌────────────────┐   │
│  │Controllers │  │  Services  │  │  Repositories  │   │
│  │            │  │            │  │                │   │
│  │ - Products │─▶│ - Products │─▶│ - JPA/Hibernate│   │
│  │ - Orders   │  │ - Orders   │  │                │   │
│  └────────────┘  └────────────┘  └────────────────┘   │
│                                           │             │
│  ┌────────────┐  ┌────────────┐         │             │
│  │   DTOs     │  │  Entities  │         │             │
│  │            │  │            │         │             │
│  │ - Request  │  │ - Product  │         │             │
│  │ - Response │  │ - Order    │         │             │
│  └────────────┘  └────────────┘         │             │
└──────────────────────────────────────────┼─────────────┘
                                           │ JDBC
                     ┌─────────────────────┴──────────────┐
                     │      PostgreSQL Database           │
                     │  ┌──────────┐  ┌──────────────┐   │
                     │  │ Products │  │    Orders    │   │
                     │  └──────────┘  └──────────────┘   │
                     │  ┌──────────────────────────────┐ │
                     │  │      Order_Items             │ │
                     │  └──────────────────────────────┘ │
                     └───────────────────────────────────┘
```

### Padrões e Práticas

- **Backend**:
  - 🏛️ Arquitetura em camadas (Controller → Service → Repository)
  - 📦 DTOs para transferência de dados
  - ✅ Validação com Bean Validation
  - 🔄 Mapeamento automático com ModelMapper
  - 🌐 CORS configurado para integração
  - 📚 Documentação automática com Swagger
  - 🗃️ Migrations com Flyway

- **Frontend**:
  - 🎯 Componentes modulares e reutilizáveis
  - 🔄 Programação reativa com RxJS Observables
  - 📝 Formulários reativos com validação
  - 🎨 Material Design para consistência visual
  - 🔌 Services para comunicação HTTP
  - 💾 Tipagem forte com TypeScript
  - 🎭 Change Detection otimizado

## 📋 Pré-requisitos

### Obrigatórios

- **Java JDK 17** ou superior
  ```bash
  java -version
  # Saída esperada: java version "17.x.x"
  ```

- **Node.js 18+** e **NPM 11+**
  ```bash
  node --version  # v18.x.x ou superior
  npm --version   # 11.x.x ou superior
  ```

- **PostgreSQL 16+**
  ```bash
  psql --version
  # Saída esperada: psql (PostgreSQL) 16.x
  ```

- **Maven 3.8+** (ou usar o wrapper incluído)
  ```bash
  mvn --version
  # Ou usar: ./mvnw --version (Linux/Mac) ou mvnw.cmd --version (Windows)
  ```

### Opcionais (Recomendados)

- **Git** - para controle de versão
- **Postman** - para testar APIs (collection incluída)
- **VSCode** ou **IntelliJ IDEA** - IDEs recomendadas

## 🔧 Instalação e Configuração

### 1️⃣ Clone o Repositório

```bash
git clone <url-do-repositorio>
cd Desafio2
```

### 2️⃣ Configurar o Banco de Dados

```bash
# Acesse o PostgreSQL
psql -U postgres

# Crie o banco de dados
CREATE DATABASE desafio2;

# Crie um usuário (opcional)
CREATE USER desafio_user WITH PASSWORD 'sua_senha';
GRANT ALL PRIVILEGES ON DATABASE desafio2 TO desafio_user;

# Saia do psql
\q
```

### 3️⃣ Configurar o Backend

Edite o arquivo `Backend/src/main/resources/application.properties`:

```properties
# Configuração do Banco de Dados
spring.datasource.url=jdbc:postgresql://localhost:5432/desafio2
spring.datasource.username=postgres
spring.datasource.password=sua_senha

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Flyway
spring.flyway.enabled=true
spring.flyway.baseline-on-migrate=true

# Porta do servidor
server.port=8080

# Swagger
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
```

### 4️⃣ Instalar Dependências do Backend

```bash
cd Backend

# Usando Maven instalado
mvn clean install

# OU usando Maven Wrapper (recomendado)
./mvnw clean install        # Linux/Mac
mvnw.cmd clean install      # Windows
```

### 5️⃣ Instalar Dependências do Frontend

```bash
cd ../Frontend
npm install
```

## ▶️ Executando o Projeto

### Iniciar o Backend

```bash
cd Backend

# Opção 1: Maven instalado
mvn spring-boot:run

# Opção 2: Maven Wrapper
./mvnw spring-boot:run        # Linux/Mac
mvnw.cmd spring-boot:run      # Windows

# Opção 3: JAR executável
mvn package
java -jar target/backenddesafio2-0.0.1-SNAPSHOT.jar
```

O backend estará rodando em: **http://localhost:8080**

### Iniciar o Frontend

```bash
cd Frontend
npm start

# Ou para desenvolvimento com live reload
ng serve
```

O frontend estará rodando em: **http://localhost:4200**

### 🎉 Acessar a Aplicação

- **Frontend**: http://localhost:4200
- **Backend API**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/api-docs

## 📁 Estrutura do Projeto

### Backend

```
Backend/
├── src/
│   ├── main/
│   │   ├── java/br/com/thallysprojetos/desafio2/
│   │   │   ├── configs/           # Configurações (CORS, Swagger)
│   │   │   ├── controllers/       # Controllers REST
│   │   │   │   ├── OrdersController.java
│   │   │   │   └── ProductsController.java
│   │   │   ├── dtos/              # Data Transfer Objects
│   │   │   │   ├── CheckoutRequestDTO.java
│   │   │   │   ├── OrderItemDTO.java
│   │   │   │   └── ProductDTO.java
│   │   │   ├── exceptions/        # Exceções customizadas
│   │   │   ├── mappers/           # Mapeadores DTO ↔ Entity
│   │   │   ├── models/            # Entidades JPA
│   │   │   │   ├── Order.java
│   │   │   │   ├── OrderItem.java
│   │   │   │   └── Product.java
│   │   │   ├── repositories/      # Repositórios JPA
│   │   │   │   ├── OrderRepository.java
│   │   │   │   └── ProductRepository.java
│   │   │   ├── services/          # Lógica de negócio
│   │   │   │   ├── OrderService.java
│   │   │   │   └── ProductService.java
│   │   │   └── BackEndChallengerTwoApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── db/migration/      # Scripts Flyway
│   │           ├── V1__scriptInicialized.sql
│   │           ├── V2__scriptDropTables.sql
│   │           ├── V3__scriptCreateTables.sql
│   │           └── V4__scriptInsert_data.sql
│   └── test/                      # Testes unitários
├── pom.xml                        # Dependências Maven
└── postman_collection.json        # Collection Postman
```

### Frontend

```
Frontend/
├── src/
│   ├── app/
│   │   ├── products/
│   │   │   ├── product/
│   │   │   │   ├── models/              # Interfaces TypeScript
│   │   │   │   │   ├── cart.model.ts
│   │   │   │   │   ├── checkout.model.ts
│   │   │   │   │   ├── order.model.ts
│   │   │   │   │   └── product.model.ts
│   │   │   │   ├── services/            # Serviços HTTP
│   │   │   │   │   ├── order.service.ts
│   │   │   │   │   └── product.service.ts
│   │   │   │   ├── product.ts           # Component
│   │   │   │   ├── product.html         # Template
│   │   │   │   └── product.scss         # Estilos
│   │   │   ├── products-module.ts
│   │   │   └── products-routing-module.ts
│   │   ├── app.ts                       # App Component
│   │   ├── app.html
│   │   ├── app.scss
│   │   └── app.routes.ts
│   ├── environments/
│   │   ├── environment.ts               # Config desenvolvimento
│   │   └── environment.prod.ts          # Config produção
│   ├── index.html
│   ├── main.ts
│   └── styles.scss
├── angular.json
├── package.json
└── tsconfig.json
```

## 🔌 API Endpoints

### Products

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/products` | Lista todos os produtos |
| `GET` | `/products/{id}` | Busca produto por ID |
| `POST` | `/products` | Cria novo produto |
| `PUT` | `/products/{id}` | Atualiza produto existente |
| `DELETE` | `/products/{id}` | Remove produto |

**Exemplo de Request (POST /products)**:
```json
{
  "name": "Notebook Dell",
  "price": 3500.00,
  "stock": 15
}
```

**Exemplo de Response**:
```json
{
  "id": 1,
  "name": "Notebook Dell",
  "price": 3500.00,
  "stock": 15
}
```

### Orders

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/cart/checkout` | Finaliza compra (checkout) |
| `GET` | `/orders` | Lista todos os pedidos |
| `GET` | `/orders/{id}` | Busca pedido por ID |

**Exemplo de Request (POST /cart/checkout)**:
```json
{
  "items": [
    {
      "productId": 1,
      "quantity": 2
    },
    {
      "productId": 3,
      "quantity": 1
    }
  ]
}
```

**Exemplo de Response**:
```json
{
  "id": 5,
  "total": 7500.00,
  "items": [
    {
      "id": 10,
      "productId": 1,
      "productName": "Notebook Dell",
      "quantity": 2,
      "price": 3500.00,
      "subtotal": 7000.00
    },
    {
      "id": 11,
      "productId": 3,
      "productName": "Mouse Gamer",
      "quantity": 1,
      "price": 500.00,
      "subtotal": 500.00
    }
  ]
}
```

### Validações

- ✅ Nome do produto: obrigatório, não pode ser vazio
- ✅ Preço: obrigatório, deve ser maior ou igual a zero
- ✅ Estoque: obrigatório, deve ser maior ou igual a zero
- ✅ Quantidade no checkout: deve ser maior que zero
- ✅ Estoque disponível: verificado antes de finalizar compra

## 🎬 Demonstração

### Tela Principal
- Lista de produtos com paginação (5, 10 ou 20 itens por página)
- Botões de ação: adicionar ao carrinho, editar, excluir
- Detalhes expandíveis com informações completas do produto
- Badge no carrinho mostrando quantidade de itens

### Carrinho de Compras
- Painel lateral deslizante
- Lista de itens com preço unitário e subtotal
- Controles de quantidade (+/-)
- Total geral calculado automaticamente
- Botão de finalizar compra

### Modais e Notificações
- Modal para adicionar/editar produtos com validação
- Notificação de sucesso (verde) com animação de check
- Notificação de erro (vermelho) com animação de shake
- Loading states durante operações assíncronas

## 🧪 Testes

### Backend

```bash
cd Backend

# Executar todos os testes
mvn test

# Executar com relatório de cobertura
mvn clean test jacoco:report

# Ver relatório em: target/site/jacoco/index.html
```

### Frontend

```bash
cd Frontend

# Executar testes unitários
npm test

# Executar com cobertura
ng test --code-coverage

# Ver relatório em: coverage/index.html
```

### Testes Manuais

Use a collection do Postman incluída: `Backend/postman_collection.json`

1. Importe a collection no Postman
2. Configure a variável `baseUrl` como `http://localhost:8080`
3. Execute os requests na ordem:
   - GET Products
   - POST Product
   - PUT Product
   - POST Checkout
   - GET Orders

## 📚 Documentação Adicional

- 📄 [QUICK_START.md](QUICK_START.md) - Guia de início rápido
- 🔗 [INTEGRATION_GUIDE.md](INTEGRATION_GUIDE.md) - Guia de integração
- 🧪 [API_TESTING_EXAMPLES.md](API_TESTING_EXAMPLES.md) - Exemplos de testes
- 📝 [CHANGES_SUMMARY.md](CHANGES_SUMMARY.md) - Resumo de mudanças
- 📖 [TESTING_GUIDE.md](Backend/TESTING_GUIDE.md) - Guia de testes backend

## 🤝 Contribuindo

Contribuições são bem-vindas! Para contribuir:

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/NovaFuncionalidade`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova funcionalidade'`)
4. Push para a branch (`git push origin feature/NovaFuncionalidade`)
5. Abra um Pull Request

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

## 👨‍💻 Autor

**Thallys Projetos**
- GitHub: [@thallysprojetos](https://github.com/thallysprojetos)

## 🙏 Agradecimentos

- Spring Boot community
- Angular team
- Material Design
- PostgreSQL developers
- Todos os contribuidores de código aberto

---

⭐ Se este projeto foi útil para você, considere dar uma estrela!

🐛 Encontrou um bug? [Abra uma issue](https://github.com/seu-usuario/desafio2/issues)

💬 Tem alguma dúvida? Entre em contato!
