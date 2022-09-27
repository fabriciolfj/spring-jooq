# Java object orientated query
- garante o tipo utilizado do dado na consulta, se é o mesmo que consta na coluna na base de dados
- garante que a coluna utilizada, existe na base de dados
- gera objetos java com base no schema do banco de dados. Abaixo alguns recursos que nos fornecem base para tal tarefa:
  - utilizar o jpa como base
  - migração do flyway como base (ddl, mais indicado)
  - arquivos xml do liquibase
  
# Revisando
- DDL - Data Definition Language - Linguagem de Definição de Dados.
  - São os comandos que interagem com os objetos do banco.
  - São comandos DDL : CREATE, ALTER e DROP

- DML - Data Manipulation Language - Linguagem de Manipulação de Dados.
  - São os comandos que interagem com os dados dentro das tabelas.
  - São comandos DML : INSERT, DELETE e UPDATE

- DQL - Data Query Language - Linguagem de Consulta de dados.
  - São os comandos de consulta.
  - São comandos DQL : SELECT (é o comando de consulta)
  
- Aqui cabe um parenteses. Em alguns livros o SELECT fica na DML em outros tem esse grupo próprio.
- DTL - Data Transaction Language - Linguagem de Transação de Dados.
  - São os comandos para controle de transação.
  - São comandos DTL : BEGIN TRANSACTION, COMMIT E ROLLBACK

- DCL - Data Control Language - Linguagem de Controle de Dados.
  - São os comandos para controlar a parte de segurança do banco de dados.
  - São comandos DCL : GRANT, REVOKE E DENY.

## Uso do JPA
- jooq olha as entidades
- mapeia para as tabelas em um H2
- faz a engenharia reversa do schema gerado no h2 (com base nas entidades jpa)
- cria-se os objetos em java

## Exemplo de uso do jooq
```
    public List<CustomerAndOrder> findCustomersAndOrders() {
        return ctx.select(Customer.CUSTOMER.CUSTOMER_NAME, ORDER.ORDER_DATE)
                .from(ORDER)
                .innerJoin(Customer.CUSTOMER)
                .using(Customer.CUSTOMER.CUSTOMER_NUMBER)
                .orderBy(ORDER.ORDER_DATE.desc())
                .fetchInto(CustomerAndOrder.class);
    }
```

## Consulta segura
- ao executar uma query podemos esperar uma lista de records e neste passar os tipos das colunas esperadas
```
final Result<Record3<String, String, BigDecimal>> result =context.select(Customer.CUSTOMER.CUSTOMER_NAME, Customerdetail.CUSTOMERDETAIL.CITY, Customer.CUSTOMER.CREDIT_LIMIT)
```
- joopq consegui acessar o tipo até o record22, acima disso torna-se inseguro, ou seja, vir um tipo não esperado,

## Cast e Coerce

### Cast
- Na consulta podemos utilizar a expressão cast, seja nas colunas esperadas no resultado ou nas condições
- essa expressão modifica o sql gerado.

### Coerce
- da mesma forma que o cast, podemos utiliza a coerção nas colunas esperadas no resultado ou nas condições
- essa expressão não modifica o sql gerado

### Qual utilizar?
- o mais indicado é o coerce, mas dependendo da situação, o uso de um ou de outro, podem gerar resultados diferentes.

## Operador val
- utilizamos quando o valor de comparação está a esquerda do campo
- quando se requer um tipo de campo para um dos parâmetros
- uso de parâmetros do usuario no meio do resultado da consulta

## Operador inline
- transforma o valor passado (inline("1")) em dado simples, diretamente na consulta sql como parâmetro (sem uso do ? para ser substituido depois)
- indicado para constantes