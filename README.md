# 🛒 Spring Store API

API RESTful para gerenciamento de estoque e catálogo de produtos, desenvolvida com foco em **Clean Code**, **Arquitetura em Camadas** e **Boas Práticas de Mercado**.

## 🚀 Tecnologias & Ferramentas

* **Java 21 LTS** (Core - Foco em estabilidade)
* **Spring Boot 3.4** (Framework principal)
* **Spring Data JPA** (Persistência de dados)
* **H2 Database** (Banco de dados em arquivo para persistência local)
* **JUnit 5 & Mockito** (Suíte de Testes Unitários)
* **OpenAPI / Swagger** (Documentação Viva e Interativa)
* **Lombok** (Redução de boilerplate)

## 🏗️ Arquitetura e Padrões

O projeto segue uma arquitetura robusta, segura e escalável:

* **Layered Architecture:** Separação estrita de responsabilidades entre `Controller` (Web), `Service` (Regra de Negócio) e `Repository` (Acesso a Dados).
* **DTO Pattern (Data Transfer Object):** Uso de Java Records para blindar a API, evitando a exposição direta das entidades JPA.
* **Global Exception Handling:** Tratamento centralizado de erros (`@RestControllerAdvice`) convertendo exceptions Java em respostas JSON amigáveis e padronizadas.
* **Validation Centralizada:** Aplicação do princípio DRY (*Don't Repeat Yourself*), com métodos de validação de negócio reutilizáveis na camada de Serviço.
* **JPA Auditing:** Gestão automática de metadados, como datas de criação (`created_at`) e última atualização (`updated_at`).

## ⚙️ Como Rodar Localmente

### Pré-requisitos
* Java 21 (JDK) instalado e configurado no PATH.

### Passos
1. **Clone o repositório:**
   ```bash
   git clone [https://github.com/pacamole/spring-store.git](https://github.com/SEU-USUARIO/spring-store.git)
   ```
2. **Entre na pasta do projeto:**
   ```bash
   cd spring-store
   ```
3. **Execute a aplicação (via Maven Wrapper):**
* No Windows:
   ```bash
   ./mvnw spring-boot:run
   ```
* No Linux/Mac
   ```bash
   ./mvnw spring-boot:run
   ```
4. **Acesse a Documentação (Swagger UI): Abra seu navegador em: 👉**
``` http://localhost:8080/swagger-ui/index.html ```

## 🧪 Testes
O projeto conta com testes unitários cobrindo as regras de negócio da camada de Serviço, utilizando Mocks para isolar dependências externas.

Para rodar a suíte de testes:
   ```bash
   ./mvnw test
   ```

## 📝 Autor
Desenvolvido como parte de um programa de mentoria avançada em Ecossistema Spring, focando na transição de conceitos teóricos para implementações de mercado.