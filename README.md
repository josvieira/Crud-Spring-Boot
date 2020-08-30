# Crud-Spring-Boot

## Problema
<p>Desenvolva um projeto Java com Spring Boot (utilizando qualquer modulo que achar necessário), que possua uma api REST com CRUD de Cliente (id, nome, cpf, dataNascimento). O CRUD deve possuir uma api de GET, POST, DELETE, PATCH e PUT.
A api de GET deve aceitar query strings para pesquisar os clientes por CPF e nome. Também é necessário que nessa api os clientes voltem paginados e que possua um campo por cliente com a idade calculado dele considerando a data de nascimento.
O banco de dados deve estar em uma imagem Docker ou em um sandbox SAAS (banco SQL).
O projeto deve estar em um repositório no BitBucket e na raiz do projeto deve conter um Postman para apreciação da api.</p>

## Tecnologias Utilizadas
- Java 8
- Spring Boot
- Spring MVC
- Spring Data JPA
- Hibernate
- Projeto Lombok 1.8.12
- Maven 4.0.0
- Docker 19.03.12
- Banco PostgreSQL
- Swagger 2.9.2

## Tecnologia Necessária Para Rodar a Aplicação
- Docker instalado

## Como Executar
1. Preiro baixe a imagem da aplicaçõa com o comando **docker pull josienevieira/testebuilders:latest** no seu terminal;
2. Suba um container para o banco de dados postgre com o comando **docker run -d --name docker-postgres -e POSTGRES_DB=builders -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=123456 postgres**;
3. Para confirmar que o container do banco está rodando execute o comando **docker ps -a**, o status, da imagem postgres, deve estar como ***up***
4. Execute a imagem baixada linkando com o container do banco que acabou de subir, a aplicação irá rodar na porta 8080, utilizando o comando: **docker run -it --link docker-postgres -p 8080:8080 josienevieira/testebuilders**

A aplicação comecará a subir e será posível ver o status pelo seu terminal.

Após subir a aplicação ela estará disponível em localhost/8080

## Como testar a Aplicação
A aplicação está com o swagger configurado, sendo possível acessar pelo endereço ***http://localhost:8080/swagger-ui.html*** no navegador.
É possível também utilizar os recursos disponíveis na API utilizando o swagger, basta clicar no ***Try It*** após clicar no endpoint desejado.

## Dificulades
- Utilizar *Docker* numa aplicação com Spring Boot e Spring Data Jpa;
 
## Melhorias
- Construir os testes unitários automatizados, nesse estágio da API nenhum teste foi implementado;
- Utilizar do Docker-Compose para orquestrar a utilização dos Containers ao invés de fazer de forma manual;
- Utilizar features mais recentes do Java;

## Aprendizados
- Tecnologia docker, conseguir entender o funcionamento e o básico para construir uma imagem e rodar um container;
- Aprendi mais sobre testes unitários com JUnit e Mockito apesar de não ter conseguido implementá-los.
- Documentação da API com Swagger;
- Padrão de projeto DTO;
 
