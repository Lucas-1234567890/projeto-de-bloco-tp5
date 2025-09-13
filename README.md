# 🛒 Sistema E-commerce API

**Projeto de Bloco - Engenharia de Dados e IA**  
**Disciplina:** Desenvolvimento Back-end [LEDC]  
**Aluno:** Lucas Amorim Porciuncula  
**Professor:** Tiago Silva

## 📋 Sobre o Projeto

Sistema de e-commerce desenvolvido em **Spring Boot** que implementa funcionalidades completas de um marketplace online, incluindo gestão de produtos, clientes, pedidos, pagamentos, rastreamento e avaliações.

### ✨ Principais Funcionalidades

- **Gestão de Produtos** - Cadastro, listagem e controle de estoque
- **Gestão de Clientes** - Cadastro com endereços múltiplos e carrinho
- **Sistema de Pedidos** - Criação e acompanhamento completo
- **Cupons de Desconto** - Sistema de promoções
- **Rastreamento** - Acompanhamento detalhado das entregas
- **Avaliações** - Sistema de review de produtos
- **Interface Console** - CRUD completo via terminal

---

## 🚀 Como Executar

### Pré-requisitos
- Java 21+
- Maven 3.8+

### Executando o Projeto
```bash
# Clone o repositório
git clone [seu-repositorio]
cd ecommerceapi

# Execute com Maven
./mvnw spring-boot:run

# Ou no Windows
mvnw.cmd spring-boot:run
```

### Acessos
- **API:** http://localhost:8080
- **Console H2:** http://localhost:8080/h2-console
  - URL: `jdbc:h2:file:C:/Users/Lucas/eclipse-workspace/ecommerceapi/data/ecommerce-db`
  - User: `sa`
  - Password: *(vazio)*

---

## 🔗 Endpoints da API

### 👥 Clientes
```
GET    /api/clientes           # Lista todos os clientes
POST   /api/clientes           # Cria novo cliente
GET    /api/clientes/{id}      # Busca cliente por ID
```

**Exemplo JSON Cliente:**
```json
{
  "nome": "João Silva",
  "email": "joao@email.com",
  "senha": "123456",
  "telefone": "(11) 99999-9999"
}
```

### 📦 Produtos
```
GET    /api/produtos           # Lista todos os produtos
POST   /api/produtos           # Cria novo produto
GET    /api/produtos/{id}      # Busca produto por ID
```

**Exemplo JSON Produto:**
```json
{
  "nome": "Smartphone",
  "descricao": "Celular Android 128GB",
  "preco": 899.99,
  "quantidadeEstoque": 50
}
```

### 🛍️ Pedidos
```
GET    /api/pedidos            # Lista todos os pedidos
POST   /api/pedidos            # Cria novo pedido
GET    /api/pedidos/{id}       # Busca pedido por ID
```

**Exemplo JSON Pedido:**
```json
{
  "cliente": {"id": 1},
  "produtos": [{"id": 1}, {"id": 2}],
  "valorTotal": 1299.98,
  "pagamento": {
    "metodo": "Cartão",
    "status": "APROVADO"
  },
  "status": "EM_PROCESSAMENTO"
}
```

### 🎟️ Cupons
```
GET    /api/cupons/validar/{codigo}  # Valida cupom por código
POST   /api/cupons                   # Cria novo cupom
```

**Exemplo JSON Cupom:**
```json
{
  "codigo": "DESC10",
  "desconto": 10.0,
  "validade": "2024-12-31T23:59:59"
}
```

### 📍 Rastreamento
```
GET    /api/rastreamentos/pedido/{pedidoId}  # Lista rastreamento do pedido
```

### ⭐ Avaliações
```
POST   /api/avaliacoes                        # Cria avaliação
GET    /api/avaliacoes/produto/{produtoId}    # Lista avaliações do produto
```

**Exemplo JSON Avaliação:**
```json
{
  "produto": {"id": 1},
  "cliente": {"id": 1},
  "nota": 5,
  "comentario": "Produto excelente!"
}
```

---

## 💻 Interface Console

O sistema também possui uma interface de terminal completa acessível ao executar a aplicação:

```
=== MENU PRINCIPAL ===
1 - Gerenciar Produtos
2 - Gerenciar Clientes  
3 - Gerenciar Carrinho / Endereços
4 - Gerenciar Cupons
5 - Gerenciar Pedidos
6 - Gerenciar Rastreamentos
7 - Gerenciar Avaliações de Produto
0 - Sair
```

### Funcionalidades por Menu:
- **Produtos:** CRUD completo + controle de estoque
- **Clientes:** CRUD + gestão de dados pessoais
- **Carrinho/Endereços:** Adicionar produtos e múltiplos endereços
- **Cupons:** Criar e gerenciar promoções
- **Pedidos:** Criar pedidos e acompanhar status
- **Rastreamento:** Adicionar etapas de entrega
- **Avaliações:** Sistema de reviews

---

## 📊 Banco de Dados

### Principais Entidades:
- **Cliente** - Dados pessoais + endereços + carrinho
- **Produto** - Catálogo + estoque
- **Pedido** - Transações + status
- **Pagamento** - Métodos + status
- **NotaFiscal** - Documentos fiscais
- **Rastreamento** - Etapas de entrega
- **AvaliacaoProduto** - Reviews dos clientes
- **Cupom** - Descontos e promoções

### Relacionamentos:
- Cliente 1:N Pedidos
- Pedido N:M Produtos  
- Pedido 1:1 Pagamento
- Pedido 1:1 NotaFiscal
- Pedido 1:N Rastreamento

---

## 🛠️ Tecnologias Utilizadas

- **Spring Boot 3.5.5** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **H2 Database** - Banco de dados em arquivo
- **Maven** - Gerenciamento de dependências
- **Java 21** - Linguagem de programação
- **Lombok** - Redução de boilerplate

---

## 📁 Estrutura do Projeto

```
src/main/java/com/lucasamorim/ecommerceapi/
├── controller/          # Endpoints REST
├── model/              # Entidades JPA
├── repository/         # Interfaces de dados
├── service/            # Lógica de negócio
├── *Console.java       # Interface terminal
└── MainConsole.java    # Menu principal
```

---

## 🎯 Casos de Uso Implementados

### Cenário Principal - Registrar Pedido:
1. Cliente seleciona produtos
2. Aplica cupom (opcional)  
3. Escolhe endereço(s) de entrega
4. Define forma de pagamento
5. Sistema processa e confirma
6. Atualiza estoque automaticamente
7. Gera nota fiscal se aprovado
8. Inicia rastreamento

### Funcionalidades Extras:
- **Múltiplos endereços** por cliente
- **Carrinho persistente** 
- **Sistema de cupons** com validação
- **Rastreamento detalhado** por etapas
- **Avaliações** pós-compra

---

## 📝 Notas do Desenvolvedor

Este projeto representa a evolução completa de um sistema e-commerce, desde a modelagem UML até a implementação funcional. Foi desenvolvido seguindo a metodologia RUP com iterações incrementais.

### Destaques Técnicos:
- Arquitetura em camadas (MVC)
- Relacionamentos JPA complexos
- Interface dupla (REST API + Console)
- Banco persistente em arquivo
- Validações e tratamento de erros

---

## 📚 Links Úteis

- **Documentação do Projeto:** [Ver PDF completo](https://docs.google.com/document/d/1YEgQOYKm9urwNSLvujy0OUtSp_zM4sRGd8D29MWlvRI/edit?tab=t.0)
- **Vídeos Explicativos:**
  - [TP2 - Casos de Uso](https://youtu.be/lxHdMg2qYBw)
  - [TP3 - Modelagem](https://www.youtube.com/watch?v=e_K5JM_Dfc0)
  - [TP4 - Implementação](https://www.youtube.com/watch?v=rTfzTUB0hTk)

---

**Desenvolvido com 💙 por Lucas Amorim Porciuncula**  
*Engenharia de Dados e IA - 2024*
