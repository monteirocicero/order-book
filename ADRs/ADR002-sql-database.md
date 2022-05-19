## ADR 002: Usar banco de dados SQL Postgres
Como MVP vamos usar um banco de dados SQL, por ser uma engine bastante conhecida e com boa documentação e fácil de usar.

## Decision
Iremos usar um banco de dados SQL.

## Racional
Banco relacionais são um modelo maduro na industria e consegue entregar uma boa performance para muitos casos de uso, como MVP usar um SQL
pode ser uma boa por ser bastante conhecido e com estratégias de melhorar de performance conhecidas.

Alternativas:
- Uma boa alternativa pode ser um banco de dados colunar como Cassandra, pois bancos colunares salvam os dados como uma única linha em disco de forma eficiente para busca.
Com isso podemos ter um throughput maior.

## Consequences
Apesar de ser um MVP começamos com um volume de dados alto, com o passar do tempo nosso banco de dados pode não atender esse volume de leitura e escrita
e poderemos ir para uma solução NoSQL que escala leitura/escrita com alto throughput.
