## ADR 001: Usar banco de dados como fila
Como MVP uma arquitetura mais centralizada, colocar um broker de fila poderia adicionar mais complexidade para testarmos nosso produto.

## Decision
Iremos usar o banco de dados como uma fila.

## Racional
Manter um novo componente na infraestrutura pode ser custoso e demorado, envolvendo silos com SRE para provisionar, monitorar e operar.
Podemos gastar muita esforço com infraestrutura e nosso produto não ter aderência ou o ROE esperado.

Alternativas:
- Provisionar um broker como RabbitMQ, Kafka como fila, ou uma solução cloud como SQS.

## Consequences
Conforme nosso volume de dados for crescendo podemos ter problemas para processar essa fila em banco, a forma de escalar será adicionar mais instâncias nos serviços, e escalar vertical o banco de dados.


