# Java object orientated query
- garante o tipo utilizado do dado na consulta, se é o mesmo que consta na coluna na base de dados
- garante que a coluna utilizada, existe na base de dados
- gera objetos java com base no schema do banco de dados. Abaixo alguns recursos que nos fornecem base para tal tarefa:
  - utilizar o jpa como base
  - migração do flyway como base (ddl, mais indicado)
  - arquivos xml do liquibase

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