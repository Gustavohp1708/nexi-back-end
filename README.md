# Nexi

A **Nexi** é uma plataforma de Inteligência Artificial criada para auxiliar empresas de forma rápida, inteligente, centralizada e totalmente personalizável.

Seu principal objetivo é facilitar o acesso a informações internas da empresa, respondendo dúvidas sobre processos, políticas, operações e qualquer outro tema relevante para o dia a dia corporativo.

---

### Front-end construído com Angular 17 (https://github.com/Gustavohp1708/nexi-front-end)
### Camada de IA: Pyhton + LangChain pring Boot.

API REST para registro e login de usuários, com configuração Spring Security, autenticação JWT e validação de credenciais com BCrypt.

## Arquitetura
- `AuthController`: endpoints `/auth/login` e `/auth/register`
- `ChatController`: endpoint `/chat` protegido e com integração de IA
- `SecurityConfig`: define regras de acesso e JWT stateless
- `TokenService`: gera/verifica tokens JWT
- `UserRepository`: persistência JPA
- `AiAgentClient`: comunicação com serviço de IA externo

## Stack
- Java 17
- Spring Boot 4
- Spring Security
- Spring Data JPA
- H2 Database
- JWT (via Auth0 Java JWT)
- Maven

## Endpoints principais
- `POST /auth/register` – registra novo usuário
- `POST /auth/login` – autentica e retorna token
- `POST /chat` – envia pergunta ao agente IA (autenticado)
