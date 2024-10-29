## Apoio Solidario API
### Descrição

A Apoio Solidario API é uma aplicação desenvolvida em Spring Boot, projetada para conectar instituições sem fins lucrativos (ONGs) a potenciais doadores. Esta API permite a criação e a gestão de ONGs, facilitando o gerenciamento dos dados associados a cada organização. A documentação da API é gerada automaticamente com o Swagger, proporcionando uma referência clara e acessível para os desenvolvedores.
### Tecnologias Utilizadas

- Java: 17
- Spring Boot: 3.3
- Banco de Dados: PostgreSQL
- Swagger: Para documentação da API


### Funcionalidades

1. Gerenciar Eventos: Adicione novos eventos associados a cada ONG.
2. Gerenciar ONGs: Edite e exclua ONGs existentes.
3. Visualizar ONGs: Liste todos as ONGs e veja detalhes específicos como projetos e eventos.

### Requisitos

- Java 17
- PostgreSQL
- Maven (para gerenciamento de dependências)

### Instalação
1.Clone o repositório:
````
git clone https://github.com/Joseulisses065/apoio-solidario-back-end.git
````
2.Navegue para o diretório do projeto:

````
cd apoio-solidario-back-end
````
3.Configure as variaveis:

````
cp TEMPLATE.env .env
````

4.Configure o banco de dados:

Certifique-se de ter criado o container, execute o seguinte comando para criar um banco de dados:

````
docker compose up -d
````
4.Verifique se o container foi inicializado:
````
docker ps
````
### Documentação da API
A documentação da API está disponível através do Swagger. Após iniciar a aplicação, acesse:

[swagger](localhost:8080/api/swagger-ui/index.html).

