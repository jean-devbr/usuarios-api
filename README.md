# Usuários API

Uma API REST para cadastro e gerenciamento de usuários, desenvolvida com Spring Boot.

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.4.1**
- **Spring Data JPA** para persistência de dados
- **Spring Validation** para validação de entrada
- **H2 Database** como banco de dados em memória
- **Lombok** para redução de boilerplate
- **MapStruct** para mapeamento de objetos
- **Maven** para gerenciamento de dependências

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
│   │       ├── UsuariosApiApplication.java          # Classe principal da aplicação
│   │       └── core/
│   │           ├── controller/
│   │           │   └── UsuarioController.java       # Controlador REST para usuários
│   │           ├── dto/
│   │           │   ├── UsuarioRequestDTO.java       # DTO para requisições
│   │           │   └── UsuarioResponseDTO.java      # DTO para respostas
│   │           ├── infra/
│   │           │   └── exceptions/
│   │           │       ├── BusinessException.java           # Exceção para erros de negócio
│   │           │       ├── ErrorMessageDTO.java            # DTO para mensagens de erro
│   │           │       ├── GlobalExceptionHandler.java      # Manipulador global de exceções
│   │           │       ├── ResourceNotFoundException.java   # Exceção para recursos não encontrados
│   │           │       └── ValidationErrorDTO.java          # DTO para erros de validação
│   │           ├── mapper/
│   │           │   └── UsuarioMapper.java           # Mapeador MapStruct
│   │           ├── model/
│   │           │   └── Usuario.java                 # Entidade Usuario
│   │           ├── repository/
│   │           │   └── UsuarioRepository.java       # Repositório JPA
│   │           └── service/
│   │               ├── UsuarioService.java          # Interface do serviço
│   │               └── impl/
│   │                   └── UsuarioServiceImpl.java  # Implementação do serviço
│   └── resources/
│       ├── application.yml                          # Configurações da aplicação
│       ├── static/                                  # Recursos estáticos
│       └── templates/                               # Templates (não utilizados)
└── test/
    └── java/
        └── com/cadastro/usuarios/
            └── UsuariosApiApplicationTests.java     # Testes da aplicação
target/
```

## Como Executar Localmente

### Pré-requisitos

- Java 21 instalado
- Maven instalado (ou use o wrapper `./mvnw`)

### Passos para Execução

1. **Clone o repositório** (se aplicável) ou navegue até a pasta do projeto.

2. **Execute a aplicação**:
   ```bash
   ./mvnw spring-boot:run
   ```
   Ou, se preferir usar Maven diretamente:
   ```bash
   mvn spring-boot:run
   ```

3. A aplicação estará rodando em `http://localhost:8085`.

## Endpoints da API

A API fornece os seguintes endpoints para gerenciamento de usuários:

- **POST /usuarios** - Criar um novo usuário
  - Corpo: `UsuarioRequestDTO` (nome e senha obrigatórios)
  - Resposta: `UsuarioResponseDTO` (id, nome, senha)

- **GET /usuarios** - Listar todos os usuários
  - Resposta: Lista de `UsuarioResponseDTO`

- **GET /usuarios/{id}** - Buscar usuário por ID
  - Parâmetro: `id` (Long)
  - Resposta: `UsuarioResponseDTO`

- **PUT /usuarios/{id}** - Atualizar usuário
  - Parâmetro: `id` (Long)
  - Corpo: `UsuarioRequestDTO`
  - Resposta: `UsuarioResponseDTO`

- **DELETE /usuarios/{id}** - Deletar usuário
  - Parâmetro: `id` (Long)
  - Resposta: 204 No Content

- **POST /usuarios/login** - Fazer login
  - Corpo: `LoginDTO` (nome e senha obrigatórios)
  - Resposta: 200 OK se autenticado, 400 Bad Request com mensagem de erro se inválido

### Exemplo de Requisição

**Fazer login:**
```bash
curl -X POST http://localhost:8085/usuarios/login \
  -H "Content-Type: application/json" \
  -d '{"nome": "João Silva", "senha": "senha123"}'
```

**Criar usuário:**
```bash
curl -X POST http://localhost:8085/usuarios \
  -H "Content-Type: application/json" \
  -d '{"nome": "João Silva", "senha": "senha123"}'
```

## Banco de Dados H2

A aplicação utiliza H2 como banco de dados em memória. Para acessar o console H2:

1. Com a aplicação rodando, acesse: `http://localhost:8085/h2-console`
2. Use as seguintes credenciais:
   - **JDBC URL**: `jdbc:h2:mem:testdb`
   - **User Name**: `sa`
   - **Password**: (deixe em branco)

## Tratamento de Erros

A API trata os seguintes tipos de erro:

- **400 Bad Request**: Erros de validação ou negócio (ex: nome ou senha duplicados)
- **404 Not Found**: Usuário não encontrado
- **500 Internal Server Error**: Erros internos do servidor

As mensagens de erro são retornadas no formato:
```json
{
  "mensagem": "Descrição do erro"
}
```

## Testes

Para executar os testes:
```bash
./mvnw test
```
