# 🧪 Guia de Testes da API

## 🚀 Iniciando a Aplicação

### 1. Subir containers Docker
```bash
docker-compose up -d
```

### 2. Iniciar a aplicação Spring Boot
```bash
mvn spring-boot:run
```

### 3. Verificar se está rodando
- Aplicação: http://localhost:8080
- Swagger: http://localhost:8080/swagger-ui.html
- RabbitMQ Management: http://localhost:15672 (admin/admin123)

## 📋 Cenários de Teste

### ✅ Teste 1: Listar Produtos
**Endpoint**: `GET /products`

**Resposta esperada**: Lista com 10 produtos pré-cadastrados

```bash
curl -X GET http://localhost:8080/products
```

---

### ✅ Teste 2: Buscar Produto por ID
**Endpoint**: `GET /products/{id}`

**Exemplo**:
```bash
curl -X GET http://localhost:8080/products/1
```

**Resposta esperada**:
```json
{
  "id": 1,
  "name": "Notebook Dell Inspiron",
  "price": 3499.99,
  "stock": 15
}
```

---

### ✅ Teste 3: Criar Produto Válido
**Endpoint**: `POST /products`

**Request**:
```bash
curl -X POST http://localhost:8080/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Mouse Gamer RGB",
    "price": 299.90,
    "stock": 50
  }'
```

**Status esperado**: `201 Created`

---

### ❌ Teste 4: Criar Produto com Preço Negativo (Deve Falhar)
**Endpoint**: `POST /products`

**Request**:
```bash
curl -X POST http://localhost:8080/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Produto Inválido",
    "price": -50.00,
    "stock": 10
  }'
```

**Status esperado**: `400 Bad Request`
**Mensagem**: "Preço não pode ser negativo"

---

### ✅ Teste 5: Atualizar Produto
**Endpoint**: `PUT /products/{id}`

**Request**:
```bash
curl -X PUT http://localhost:8080/products/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Notebook Dell Inspiron - Atualizado",
    "price": 3299.99,
    "stock": 20
  }'
```

**Status esperado**: `200 OK`

---

### ✅ Teste 6: Checkout com Estoque Suficiente
**Endpoint**: `POST /cart/checkout`

**Request**:
```bash
curl -X POST http://localhost:8080/cart/checkout \
  -H "Content-Type: application/json" \
  -d '{
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
  }'
```

**Status esperado**: `201 Created`
**Resultado**: 
- Pedido criado com sucesso
- Estoque diminuído automaticamente
- Total calculado corretamente

---

### ❌ Teste 7: Checkout com Estoque Insuficiente (Deve Falhar)
**Endpoint**: `POST /cart/checkout`

**Request**:
```bash
curl -X POST http://localhost:8080/cart/checkout \
  -H "Content-Type: application/json" \
  -d '{
    "items": [
      {
        "productId": 1,
        "quantity": 1000
      }
    ]
  }'
```

**Status esperado**: `422 Unprocessable Entity`
**Mensagem**: "Estoque insuficiente para o produto..."

---

### ✅ Teste 8: Listar Todos os Pedidos
**Endpoint**: `GET /cart/orders`

**Request**:
```bash
curl -X GET http://localhost:8080/cart/orders
```

**Resposta esperada**: Lista de todos os pedidos realizados

---

### ✅ Teste 9: Buscar Pedido por ID
**Endpoint**: `GET /cart/orders/{id}`

**Request**:
```bash
curl -X GET http://localhost:8080/cart/orders/1
```

**Resposta esperada**:
```json
{
  "id": 1,
  "items": [
    {
      "id": 1,
      "productId": 1,
      "productName": "Notebook Dell Inspiron",
      "quantity": 2,
      "price": 3499.99
    }
  ],
  "total": 6999.98,
  "createdAt": "2024-01-01T10:00:00"
}
```

---

### ✅ Teste 10: Deletar Produto
**Endpoint**: `DELETE /products/{id}`

**Request**:
```bash
curl -X DELETE http://localhost:8080/products/1
```

**Status esperado**: `204 No Content`

---

## 🎯 Validações das Regras de Negócio

### ✅ Regra 1: Produto não pode ter preço negativo
- ✅ Validação no DTO (`@PositiveOrZero`)
- ✅ Validação na Service
- ✅ Retorna erro 400 se tentar cadastrar

### ✅ Regra 2: Checkout diminui estoque
1. Verificar estoque inicial: `GET /products/1`
2. Fazer checkout com 2 unidades
3. Verificar estoque final: `GET /products/1`
4. Confirmar que diminuiu 2 unidades

### ✅ Regra 3: Bloqueia venda se estoque insuficiente
1. Tentar fazer checkout com quantidade > estoque
2. API retorna erro 422
3. Mensagem clara sobre estoque insuficiente
4. Estoque permanece inalterado

---

## 🧰 Testando via Postman

1. Importe o arquivo `postman_collection.json`
2. A collection já contém todos os endpoints configurados
3. Execute os testes na ordem sugerida

---

## 🐛 Verificação de Erros

### Verificar logs da aplicação
```bash
# Ver logs em tempo real
tail -f logs/application.log
```

### Verificar banco de dados
```bash
# Conectar ao PostgreSQL
docker exec -it desafio2-postgres psql -U postgres -d modelo_db

# Listar produtos
SELECT * FROM products;

# Listar pedidos
SELECT * FROM orders;

# Listar itens dos pedidos
SELECT * FROM order_items;
```

---

## ✅ Checklist de Funcionalidades

- [x] GET /products - Listar produtos
- [x] POST /products - Criar produto
- [x] PUT /products/{id} - Atualizar produto
- [x] DELETE /products/{id} - Deletar produto
- [x] POST /cart/checkout - Realizar checkout
- [x] GET /cart/orders - Listar pedidos
- [x] GET /cart/orders/{id} - Buscar pedido
- [x] Validação: Preço não negativo
- [x] Validação: Estoque insuficiente
- [x] Diminuir estoque no checkout
- [x] Documentação Swagger
- [x] Tratamento de erros
- [x] PostgreSQL configurado
- [x] RabbitMQ disponível
- [x] Docker Compose

---

## 📊 Testes de Performance (Opcional)

### Teste de carga com Apache Bench
```bash
# Testar listagem de produtos (100 requests)
ab -n 100 -c 10 http://localhost:8080/products
```

---

## 🎓 Dicas de Teste

1. **Sempre teste os cenários de erro** (preço negativo, estoque insuficiente)
2. **Verifique o banco de dados** após operações importantes
3. **Use o Swagger** para documentação visual
4. **Monitore os logs** para debug
5. **Teste transações** (checkout deve ser atômico)

---

**Boa sorte com os testes! 🚀**
