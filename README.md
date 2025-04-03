```md
# REST API com Spring Boot e Java + Frontend Angular

Este projeto é composto por uma **API REST** desenvolvida com **Spring Boot 3.4.0** e um **frontend em Angular**, utilizando **PostgreSQL** como banco de dados e documentação via **SpringDoc OpenAPI**.

---

## 📌 Tecnologias Utilizadas

### Backend
- Java 21
- Spring Boot 3.4.0
- Spring Web
- Spring Data JPA
- PostgreSQL
- Spring Boot DevTools
- SpringDoc OpenAPI (Swagger UI)
- Maven

### Frontend (Será iniciado)
- Angular (versão a definir)
- TypeScript
- HTML/CSS
- Angular Material (opcional)
- Consumo de API via HttpClient

---

## 🚀 Como Executar o Projeto

### 1️⃣ Pré-requisitos

- [JDK 21](https://www.oracle.com/java/technologies/javase-jdk21-downloads.html) instalado
- [Maven](https://maven.apache.org/download.cgi) instalado
- [Node.js](https://nodejs.org/) e npm instalados
- [Angular CLI](https://angular.io/cli) instalado (`npm install -g @angular/cli`)
- PostgreSQL configurado e em execução

---

## 📂 Configuração do Banco de Dados

Antes de rodar o projeto, configure seu PostgreSQL e crie um banco de dados.  
Exemplo de criação:

```sql
CREATE DATABASE minha_api;
```

Altere as credenciais no `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/minha_api
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
```

---

## 🔥 Rodando a Aplicação

### Backend (Spring Boot)

Execute o seguinte comando:

```sh
./mvnw spring-boot:run
```

Ou, no Windows:

```sh
mvnw.cmd spring-boot:run
```

A API será iniciada em `http://localhost:8080`.

### Frontend (Angular)

Acesse a pasta do frontend:

```sh
cd frontend
```

Instale as dependências:

```sh
npm install
```

Inicie o servidor:

```sh
ng serve
```

O frontend será iniciado em `http://localhost:4200` e consumirá a API do backend.

---

## 📖 Documentação da API

A documentação da API está disponível via Swagger UI:

- Acesse: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## 🛠 Endpoints Disponíveis

| Método  | Endpoint   | Descrição |
|---------|-----------|------------|
| GET     | `/api/...` | Lista recursos |
| POST    | `/api/...` | Cria um novo recurso |
| PUT     | `/api/...` | Atualiza um recurso |
| DELETE  | `/api/...` | Remove um recurso |

---

## ✅ Testes

Os testes do backend podem ser executados com:

```sh
./mvnw test
```

Os testes do frontend podem ser executados com:

```sh
ng test
```

---

## 📜 Licença
Este projeto é um demo para estudos com Spring Boot e Angular.
```
