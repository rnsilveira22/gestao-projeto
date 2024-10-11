# Projetos e Funcionários API

API RESTful para gerenciar projetos e funcionários.

## Tecnologias Utilizadas

- Java 17
- Spring Boot
- PostgreSQL
- Maven

## Pré-requisitos

- Java 17
- PostgreSQL
- Maven

## Configuração do Banco de Dados

1. Crie um banco de dados no PostgreSQL.
2. Atualize as configurações do banco de dados no arquivo `src/main/resources/application.properties`.

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/seu_banco_de_dados
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```
## Documentação Endpoint
http://localhost:8080/v3/api-docs
