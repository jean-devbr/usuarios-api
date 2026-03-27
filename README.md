# Usuários API

Uma API REST para cadastro e gerenciamento de usuários, desenvolvida com Spring Boot.

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.4.1**
- **Spring Data JPA**
- **Spring Validation**
- **Spring Security**
- **H2 Database** (em memória)
- **Lombok**
- **MapStruct**
- **Maven**
- **Springdoc OpenAPI (Swagger UI)**

## Estrutura do Projeto

```
.gitignore
.gitattributes
.idea/
.mvn/
HELP.md
mvnw
mvnw.cmd
pom.xml
src/
├── main/
│   ├── java/
│   │   └── com/cadastro/usuarios/
│   │       ├── UsuariosApiApplication.java
│   │       ├── core/
│   │       │   ├── controller/
│   │       │   │   ├── TokenController.java
│   │       │   │   └── UsuarioController.java
│   │       │   ├── dto/
│   │       │   │   ├── cadastro/
│   │       │   │   │   ├── UsuarioRequestDTO.java
│   │       │   │   │   └── UsuarioResponseDTO.java
│   │       │   │   └── login/
│   │       │   │       ├── LoginRequestDTO.java
│   │       │   │       └── LoginResponseDTO.java
│   │       │   ├── entity/
│   │       │   │   └── UsuarioEntity.java
│   │       │   ├── mapper/
│   │       │   │   └── UsuarioMapper.java
│   │       │   ├── model/
│   │       │   │   └── Usuario.java
│   │       │   ├── repository/
│   │       │   │   └── UsuarioRepository.java
│   │       │   └── service/
│   │       │       ├── UsuarioService.java
│   │       │       └── impl/
│   │       │           └── UsuarioServiceImpl.java
│   │       └── infra/
│   │           ├── config/
│   │           │   ├── OpenApiConfig.java
│   │           │   └── SecurityConfig.java
│   │           ├── exceptions/
│   │           │   ├── BusinessException.java
│   │           │   ├── ErrorMessageDTO.java
│   │           │   ├── GlobalExceptionHandler.java
│   │           │   ├── ResourceNotFoundException.java
│   │           │   └── ValidationErrorDTO.java
│   │           └── security/
│   │               └── ApiKeyAuthFilter.java
│   └── resources/
│       ├── application.yml
│       ├── app.key
│       ├── app.pub
│       ├── static/
│       └── templates/
└── test/
    └── java/
        └── com/cadastro/usuarios/
            └── UsuariosApiApplicationTests.java
```

## Como Executar Localmente

### Pre-requisitos

- Java 21 instalado
- Maven instalado (ou use o wrapper `./mvnw`)

### Passos

```bash
./mvnw spring-boot:run
```

A aplicacao sobe em `http://localhost:8085`.

## Autenticacao (API Key)

As rotas protegidas exigem o header:

```
X-API-KEY: <sua-chave>
```

A chave fica em `src/main/resources/application.yml`:

```
api:
  key: changeme-123
```

Rotas publicas:
- `/swagger-ui/**`
- `/v3/api-docs/**`
- `/usuarios/login`
- `/h2-console/**`

## Swagger UI

- URL: `http://localhost:8085/swagger-ui/index.html`
- Clique em **Authorize** e informe a API key (`X-API-KEY`).

## Endpoints Principais

- **POST /usuarios** - Criar usuario
- **GET /usuarios** - Listar usuarios
- **GET /usuarios/{id}** - Buscar por ID
- **PUT /usuarios/{id}** - Atualizar usuario
- **DELETE /usuarios/{id}** - Remover usuario
- **POST /usuarios/login** - Login

### Exemplo (Criar usuario)

```bash
curl -X POST http://localhost:8085/usuarios \
  -H "Content-Type: application/json" \
  -H "X-API-KEY: changeme-123" \
  -d '{"nome":"Joao","senha":"123"}'
```

### Exemplo (Login)

```bash
curl -X POST http://localhost:8085/usuarios/login \
  -H "Content-Type: application/json" \
  -d '{"nome":"Joao","senha":"123"}'
```

## Banco de Dados H2

- Console: `http://localhost:8085/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- User: `sa`
- Password: (vazio)

## Testes

```bash
./mvnw test
```
