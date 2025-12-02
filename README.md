# 🛒 Spring Store API

API RESTful para gerenciamento de estoque e catálogo de produtos, desenvolvida com foco em **Clean Code**, **Arquitetura em Camadas**, **Segurança** e **Escalabilidade**.

## 🚀 Tecnologias & Ferramentas

* **Java 21 LTS** (Core)
* **Spring Boot 3.4** (Framework)
* **Spring Data JPA** (Persistência)
* **Spring Security + JWT** (Autenticação Stateless)
* **H2 Database** (Banco em arquivo para dev rápido)
* **PostgreSQL** (Banco de produção via Docker)
* **Docker & Docker Compose** (Containerização)
* **JUnit 5 & Mockito** (Testes Unitários)
* **OpenAPI / Swagger** (Documentação Viva)
* **Lombok** (Produtividade)

## 🏗️ Arquitetura e Padrões

O projeto segue uma arquitetura híbrida e robusta:

* **Layered Architecture:** Separação estrita (Controller, Service, Repository).
* **DTO Pattern:** Blindagem da API contra exposição de entidades e *Mass Assignment*.
* **Secure by Design:**
    * Autenticação via **Tokens JWT**.
    * Senhas criptografadas com **BCrypt**.
    * Proteção contra acesso a recursos sem credenciais.
* **Global Exception Handling:** Tratamento centralizado de erros (`@RestControllerAdvice`).
* **Validation Centralizada:** Regras de negócio reutilizáveis e princípio DRY.
* **Infraestrutura como Código:** Ambiente completo configurado via `docker-compose`.

## ⚙️ Como Rodar (Escolha seu Modo)

O projeto suporta dois modos de execução via **Spring Profiles**.

### Opção A: Modo Dev (Rápido) ⚡
Usa banco **H2 em arquivo**. Ideal para testes rápidos sem instalar nada.

1. **Clone o repositório:**
   ```bash
   git clone [https://github.com/SEU-USUARIO/spring-store.git](https://github.com/pacamole/spring-store.git)
   cd spring-store
   ```
2. Execute (Maven Wrapper):
- Windows: `./mvnw spring-boot:run`
- Linux/Mac: `./mvnw spring-boot:run`

3. Acesse: `http://localhost:8080/swagger-ui/index.html`
---
### Opção B: Modo Produção (Docker) 🐳
Sobe a API junto com um banco **PostgreSQL** real em containers isolados.

**Pré-requisito**: Docker Desktop instalado.
1. Na raiz do projeto, execute:

   ```Bash
   docker-compose up --build
   ```
2. O que acontece:

- O Docker baixa o PostgreSQL.
- O Docker compila a aplicação (Multi-stage build).
- A API sobe conectada ao Postgres automaticamente.

3. Acesse: http://localhost:8080/swagger-ui/index.html
**Nota**: Os dados do PostgreSQL são persistidos no volume postgres-data.

## 🧪 Testes
Para rodar a suíte de testes unitários:
   ```Bash
   ./mvnw test
   ```