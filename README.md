# 🛒 Sistema de Produtos e Carrinho de Compras

Um pequeno sistema de produtos com carrinho de compras e cálculo automático de total. Aplicação full-stack moderna que permite gerenciar produtos, adicionar itens ao carrinho e realizar compras com controle de estoque em tempo real.

![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.5-brightgreen?style=flat-square&logo=spring)
![Angular](https://img.shields.io/badge/Angular-21-red?style=flat-square&logo=angular)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?style=flat-square&logo=postgresql)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-3.13-orange?style=flat-square&logo=rabbitmq)
![Docker](https://img.shields.io/badge/Docker-Compose-blue?style=flat-square&logo=docker)
![Material Design](https://img.shields.io/badge/Material%20Design-UI-purple?style=flat-square&logo=material-design)

## 🌐 Deploy em Produção

**🚀 Aplicação disponível online:**

- **Frontend**: [https://sistema-de-produtos-e-carrinho-frontend.onrender.com](https://sistema-de-produtos-e-carrinho-frontend.onrender.com)
- **Backend API**: [https://sistema-de-produtos-e-carrinho.onrender.com](https://sistema-de-produtos-e-carrinho.onrender.com)
- **Documentação Swagger**: [https://sistema-de-produtos-e-carrinho.onrender.com/swagger-ui.html](https://sistema-de-produtos-e-carrinho.onrender.com/swagger-ui.html)

> ⚠️ **IMPORTANTE**: O serviço de hospedagem (Render) coloca o site em modo inativo (sleep mode) após 15 minutos sem acesso. Caso encontre um erro ao acessar pela primeira vez, por favor, **aguarde cerca de 30-60 segundos** para que o serviço seja reativado automaticamente. Após o primeiro acesso, a aplicação ficará rápida e responsiva.

## 📋 Índice

- [Deploy em Produção](#-deploy-em-produção)
- [Sobre o Projeto](#-sobre-o-projeto)
- [Funcionalidades](#-funcionalidades)
- [Tecnologias](#-tecnologias)
- [Arquitetura](#-arquitetura)
- [Docker e Containerização](#-docker-e-containerização)
- [Pré-requisitos](#-pré-requisitos)
- [Instalação e Configuração](#-instalação-e-configuração)
- [Executando o Projeto](#-executando-o-projeto)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [API Endpoints](#-api-endpoints)
- [Demonstração](#-demonstração)
- [Testes](#-testes)
- [Contribuindo](#-contribuindo)

## 📖 Sobre o Projeto

Este projeto é uma aplicação completa de e-commerce simplificada que demonstra a integração entre um backend robusto em Spring Boot e um frontend moderno em Angular, com arquitetura de microsserviços e mensageria assíncrona. O sistema permite:

- **Gerenciamento de Produtos**: CRUD completo com validações
- **Carrinho de Compras**: Adicionar, remover e ajustar quantidades
- **Controle de Estoque**: Validação automática e atualização em tempo real
- **Finalização de Pedidos**: Checkout com processamento assíncrono via RabbitMQ
- **Interface Responsiva**: Design moderno com Material Design
- **Containerização**: Docker e Docker Compose para ambientes isolados
- **Deploy em Produção**: Hospedado no Render com CloudAMQP

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
- ✅ Processamento assíncrono via RabbitMQ
- ✅ Resposta imediata ao usuário (não bloqueia UI)
- ✅ Atualização automática de estoque após compra
- ✅ Geração de pedido com ID único
- ✅ Notificações visuais de sucesso/erro
- ✅ Limpeza automática do carrinho após compra
- ✅ Dead Letter Queue para tratamento de erros

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
| **Spring AMQP** | 3.5.5 | Integração com RabbitMQ |
| **PostgreSQL** | 16+ | Banco de dados relacional |
| **RabbitMQ** | 3.13+ | Message broker para processamento assíncrono |
| **Flyway** | Latest | Controle de versionamento do banco de dados |
| **Lombok** | Latest | Redução de boilerplate code |
| **ModelMapper** | 3.2.4 | Mapeamento entre DTOs e Entities |
| **SpringDoc OpenAPI** | 2.8.13 | Documentação Swagger/OpenAPI |
| **Maven** | 3.8+ | Gerenciamento de dependências |
| **Docker** | Latest | Containerização da aplicação |

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
| **Nginx** | Latest | Servidor web e proxy reverso (produção) |

### DevOps & Infraestrutura

| Tecnologia | Versão | Descrição |
|------------|--------|-----------|
| **Docker** | Latest | Containerização de aplicações |
| **Docker Compose** | Latest | Orquestração de containers |
| **Render** | Cloud | Plataforma de deploy (PaaS) |
| **CloudAMQP** | Cloud | RabbitMQ gerenciado (CloudAMQP Lemur) |
| **Nginx** | Alpine | Servidor web para frontend em produção |
| **Maven Wrapper** | Included | Build do backend sem Maven instalado |

## 🏗️ Arquitetura

### Arquitetura Geral (com Mensageria Assíncrona)

```
┌─────────────────────────────────────────────────────────────┐
│                    FRONTEND (Angular 21)                    │
│  ┌────────────┐  ┌────────────┐  ┌────────────────┐       │
│  │ Components │  │  Services  │  │     Models     │       │
│  │            │  │            │  │                │       │
│  │ - Product  │  │ - Product  │  │ - Product      │       │
│  │ - Cart     │  │ - Order    │  │ - CartItem     │       │
│  │ - Modal    │  │ - HTTP     │  │ - Order        │       │
│  └────────────┘  └────────────┘  └────────────────┘       │
└────────────────────┬────────────────────────────────────────┘
                     │ HTTP/REST (JSON)
                     │ Dev: 4200→8080 | Prod: /api→Backend
┌────────────────────┴────────────────────────────────────────┐
│               BACKEND (Spring Boot 3.5.5)                   │
│  ┌────────────┐  ┌────────────┐  ┌────────────────┐       │
│  │Controllers │  │  Services  │  │  Repositories  │       │
│  │            │  │            │  │                │       │
│  │ - Products │─▶│ - Products │─▶│ - JPA/Hibernate│       │
│  │ - Orders   │  │ - Orders   │  │                │       │
│  └────────────┘  └─────┬──────┘  └────────────────┘       │
│                        │                  │                 │
│                        │ ④ Publish        │ ③ Save Order    │
│                        ▼                  ▼                 │
│  ┌──────────────────────────────┐  ┌──────────────┐       │
│  │   RabbitMQ Publisher         │  │ PostgreSQL   │       │
│  │  (OrderMessagePublisher)     │  │   Database   │       │
│  └──────────────┬───────────────┘  └──────────────┘       │
│                 │                           ▲               │
│                 │                           │ ⑥ Update      │
│  ┌──────────────▼───────────────┐          │               │
│  │   RabbitMQ Consumer          │──────────┘               │
│  │  (OrderMessageConsumer)      │                          │
│  │  - Processa assíncrono       │                          │
│  │  - Valida estoque            │                          │
│  │  - Atualiza status           │                          │
│  └──────────────────────────────┘                          │
└──────────────────┬──────────────────────────────────────────┘
                   │ AMQP (SSL)
            ┌──────┴──────────────────────────────┐
            │       RabbitMQ (CloudAMQP)          │
            │  ┌─────────────┐  ┌──────────────┐ │
            │  │   Exchange  │  │    Queue     │ │
            │  │order.exchange  │ order.queue  │ │
            │  └─────────────┘  └──────────────┘ │
            │  Routing Key: order.created         │
            └─────────────────────────────────────┘
```

**Fluxo de Checkout:**
1. Frontend envia POST /checkout
2. Backend responde **imediatamente** com 200 OK + Order ID
3. Backend salva pedido no PostgreSQL
4. Backend publica mensagem no RabbitMQ
5. Consumer processa assíncrono (não bloqueia usuário)
6. Consumer atualiza status do pedido

### Padrões e Práticas

- **Backend**:
  - 🏛️ Arquitetura em camadas (Controller → Service → Repository)
  - 📦 DTOs para transferência de dados
  - ✅ Validação com Bean Validation
  - 🔄 Mapeamento automático com ModelMapper
  - 🐰 Mensageria assíncrona com RabbitMQ (AMQP)
  - 🌐 CORS dinâmico (dev/prod)
  - 📚 Documentação automática com Swagger
  - 🗃️ Migrations com Flyway
  - 🔧 Spring Profiles (dev/prod)
  - 🐳 Containerizado com Docker

- **Frontend**:
  - 🎯 Componentes modulares e reutilizáveis
  - 🔄 Programação reativa com RxJS Observables
  - 📝 Formulários reativos com validação
  - 🎨 Material Design para consistência visual
  - 🔌 Services para comunicação HTTP
  - 💾 Tipagem forte com TypeScript
  - 🎭 Change Detection otimizado
  - 🧹 Cleanup automático de subscriptions (destroy$)
  - 🌐 Environments (dev/prod)
  - 🐳 Multi-stage build com Nginx

## 🐳 Docker e Containerização

O projeto está completamente dockerizado para facilitar o desenvolvimento e deploy:

### Ambiente de Desenvolvimento Local

```bash
# Subir todos os serviços com um comando
docker-compose up -d

# Serviços incluídos:
# - PostgreSQL (porta 5433)
# - RabbitMQ (portas 5673, 15673)
# - Backend Spring Boot (porta 8081)
# - Frontend Angular (porta 80)
```

### Características do Docker Setup

- **Multi-stage Builds**: Otimização de tamanho das imagens
  - Backend: Maven build → JRE runtime (Alpine)
  - Frontend: Node build → Nginx serve (Alpine)

- **Healthchecks**: Garantem que serviços estejam prontos
  - PostgreSQL: `pg_isready`
  - RabbitMQ: Management API check

- **Volumes Nomeados**: Persistência de dados
  - `postgres_data`: Dados do PostgreSQL
  - `rabbitmq_data`: Mensagens do RabbitMQ

- **Variáveis de Ambiente**: Configuração flexível
  - `.env` para desenvolvimento local
  - Environment variables no Render para produção

### Comandos Úteis Docker

```bash
# Ver logs de um serviço
docker-compose logs -f backend

# Reiniciar um serviço específico
docker-compose restart backend

# Parar todos os serviços
docker-compose down

# Parar e remover volumes (limpar dados)
docker-compose down -v

# Rebuild de imagens
docker-compose build --no-cache
```

### Deploy em Produção (Render)

O projeto está configurado para deploy automatizado no Render:

- **Backend**: Web Service com Docker
  - Build context: `Backend`
  - Dockerfile: `Backend/Dockerfile`
  - Environment: Spring Profile `prod`

- **Frontend**: Static Site com Nginx
  - Build context: `Frontend`
  - Dockerfile: `Frontend/Dockerfile`
  - Proxy reverso para Backend via `/api`

- **Banco de Dados**: PostgreSQL gerenciado pelo Render

- **Mensageria**: RabbitMQ gerenciado pelo CloudAMQP

## 📋 Pré-requisitos

### Opção 1: Desenvolvimento com Docker (Recomendado)

- **Docker** e **Docker Compose**
  ```bash
  docker --version        # Docker 20.x.x ou superior
  docker-compose --version # 2.x.x ou superior
  ```

**Com Docker, você NÃO precisa instalar:** Java, Node.js, PostgreSQL, RabbitMQ ou Maven. Tudo roda em containers isolados!

### Opção 2: Desenvolvimento Local (Sem Docker)

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

- **RabbitMQ 3.13+**
  ```bash
  rabbitmq-server --version
  # Ou usar Docker: docker run -d -p 5672:5672 -p 15672:15672 rabbitmq:3-management
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
git clone https://github.com/ThallysCezar/Sistema-de-produtos-e-carrinho.git
cd Sistema-de-produtos-e-carrinho
```

### 2️⃣ Escolha seu Ambiente de Desenvolvimento

#### 🐳 Opção A: Com Docker (Mais Rápido e Simples)

```bash
# 1. Configure as variáveis de ambiente (opcional, já tem valores padrão)
cp .env.example .env

# 2. Suba todos os serviços
docker-compose up -d

# 3. Aguarde os serviços iniciarem (cerca de 30 segundos)
docker-compose logs -f

# 4. Acesse a aplicação
# Frontend: http://localhost
# Backend: http://localhost:8081
# RabbitMQ Management: http://localhost:15673 (guest/guest)
```

Pronto! Sua aplicação está rodando. Pule para a seção [Acessar a Aplicação](#-acessar-a-aplicação).

---

#### 💻 Opção B: Sem Docker (Configuração Manual)

##### 1. Configurar o Banco de Dados

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

##### 2. Instalar e Configurar RabbitMQ

```bash
# Opção 1: Docker (mais fácil)
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management

# Opção 2: Instalação local
# Windows: https://www.rabbitmq.com/install-windows.html
# Linux: sudo apt install rabbitmq-server
# Mac: brew install rabbitmq
```

##### 3. Configurar o Backend

Edite o arquivo `Backend/src/main/resources/application-dev.properties`:

```properties
# Banco de Dados Local
spring.datasource.url=jdbc:postgresql://localhost:5432/desafio2
spring.datasource.username=postgres
spring.datasource.password=sua_senha

# RabbitMQ Local
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

# JPA/Hibernate (cria tabelas automaticamente em dev)
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Flyway
spring.flyway.enabled=true
spring.flyway.baseline-on-migrate=true

# Porta do servidor
server.port=8080
```

##### 4. Instalar Dependências do Backend

```bash
cd Backend

# Usando Maven instalado
mvn clean install

# OU usando Maven Wrapper (recomendado)
./mvnw clean install        # Linux/Mac
mvnw.cmd clean install      # Windows
```

##### 5. Instalar Dependências do Frontend

```bash
cd ../Frontend
npm install
```

---

## ▶️ Executando o Projeto

### 🐳 Com Docker

```bash
# Iniciar todos os serviços
docker-compose up -d

# Ver logs em tempo real
docker-compose logs -f

# Parar todos os serviços
docker-compose down
```

### 💻 Sem Docker

#### Iniciar o Backend

```bash
cd Backend

# Definir profile de desenvolvimento
export SPRING_PROFILES_ACTIVE=dev  # Linux/Mac
set SPRING_PROFILES_ACTIVE=dev     # Windows CMD
$env:SPRING_PROFILES_ACTIVE="dev"  # Windows PowerShell

# Opção 1: Maven instalado
mvn spring-boot:run

# Opção 2: Maven Wrapper (recomendado)
./mvnw spring-boot:run        # Linux/Mac
mvnw.cmd spring-boot:run      # Windows

# Opção 3: JAR executável
mvn package
java -jar target/backenddesafio2-0.0.1-SNAPSHOT.jar
```

O backend estará rodando em: **http://localhost:8080**

#### Iniciar o Frontend

```bash
cd Frontend
npm start

# Ou para desenvolvimento com live reload
ng serve
```

O frontend estará rodando em: **http://localhost:4200**

### 🎉 Acessar a Aplicação

#### Local (Com Docker):
- **Frontend**: http://localhost
- **Backend API**: http://localhost:8081
- **RabbitMQ Management**: http://localhost:15673 (guest/guest)
- **Swagger UI**: http://localhost:8081/swagger-ui.html
- **API Docs**: http://localhost:8081/api-docs

#### Local (Sem Docker):
- **Frontend**: http://localhost:4200
- **Backend API**: http://localhost:8080
- **RabbitMQ Management**: http://localhost:15672 (guest/guest)
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/api-docs

#### Produção (Render):
- **Frontend**: https://sistema-de-produtos-e-carrinho-frontend.onrender.com
- **Backend API**: https://sistema-de-produtos-e-carrinho.onrender.com
- **Swagger UI**: https://sistema-de-produtos-e-carrinho.onrender.com/swagger-ui.html

> ⚠️ **Lembre-se**: Aguarde 30-60 segundos no primeiro acesso à produção (wake up do Render)

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
│   │   │   │   ├── ProductService.java
│   │   │   │   ├── OrderMessagePublisher.java   # Publica no RabbitMQ
│   │   │   │   └── OrderMessageConsumer.java    # Consome do RabbitMQ
│   │   │   └── BackEndChallengerTwoApplication.java
│   │   └── resources/
│   │       ├── application.properties           # Config base
│   │       ├── application-dev.properties       # Config dev (local)
│   │       ├── application-prod.properties      # Config prod (Render)
│   │       └── db/migration/                    # Scripts Flyway
│   │           ├── V1__scriptInicialized.sql
│   │           ├── V2__scriptDropTables.sql
│   │           ├── V3__scriptCreateTables.sql
│   │           └── V4__scriptInsert_data.sql
│   └── test/                                    # Testes unitários
├── Dockerfile                                   # Docker multi-stage build
├── pom.xml                                      # Dependências Maven
├── postman_collection.json                      # Collection Postman
├── RENDER_DEPLOY.md                             # Guia de deploy backend
└── TESTING_GUIDE.md                             # Guia de testes
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
├── Dockerfile                          # Docker multi-stage build
├── nginx.conf                          # Nginx config com proxy reverso
├── angular.json
├── package.json
├── tsconfig.json
└── RENDER_FRONTEND_DEPLOY.md           # Guia de deploy frontend
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
- 🚀 [RENDER_DEPLOY.md](Backend/RENDER_DEPLOY.md) - Deploy do backend no Render
- 🌐 [RENDER_FRONTEND_DEPLOY.md](Frontend/RENDER_FRONTEND_DEPLOY.md) - Deploy do frontend no Render
- 📊 [APRESENTACAO_ROTEIRO.md](APRESENTACAO_ROTEIRO.md) - Roteiro de apresentação do projeto
- 🐳 [docker-compose.yml](docker-compose.yml) - Configuração Docker Compose
- 📋 [.env.example](.env.example) - Exemplo de variáveis de ambiente

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

**Thallys Cezar**
- GitHub: [@ThallysCezar](https://github.com/ThallysCezar)
- LinkedIn: [Thallys Cezar](https://www.linkedin.com/in/thallyscezar)
- Repositório: [Sistema-de-produtos-e-carrinho](https://github.com/ThallysCezar/Sistema-de-produtos-e-carrinho)

## 🙏 Agradecimentos

- **Grupo Moura** - Pela oportunidade do desafio técnico
- **Spring Boot Community** - Framework robusto e documentação excelente
- **Angular Team** - Framework moderno e ferramentas incríveis
- **RabbitMQ** - Mensageria confiável e escalável
- **PostgreSQL** - Banco de dados poderoso e open-source
- **Docker** - Containerização que simplifica deploy
- **Render** - Plataforma de deploy moderna e gratuita
- **CloudAMQP** - RabbitMQ gerenciado na nuvem
- **Material Design** - UI components lindos e acessíveis
- **Todos os contribuidores de código aberto** que tornam isso possível

---

## 🎯 Status do Projeto

✅ **Em Produção** - Aplicação rodando e acessível online

**Principais Features:**
- ✅ CRUD completo de produtos
- ✅ Carrinho de compras funcional
- ✅ Checkout com processamento assíncrono
- ✅ RabbitMQ para mensageria
- ✅ Docker e Docker Compose
- ✅ Deploy em produção (Render)
- ✅ Documentação completa
- ✅ Testes unitários
- ⏳ CI/CD Pipeline (próximo passo)
- ⏳ Autenticação JWT (próximo passo)

---

⭐ Se este projeto foi útil para você, considere dar uma estrela no GitHub!

🐛 Encontrou um bug? [Abra uma issue](https://github.com/ThallysCezar/Sistema-de-produtos-e-carrinho/issues)

💬 Tem alguma dúvida ou sugestão? Entre em contato!

🚀 Quer contribuir? Pull requests são bem-vindos!
