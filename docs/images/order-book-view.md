## Order Book Service View
Serviço que será responsável por receber ordens de compra e venda de Vibranium e fazer o matching das ordens.

![](architecture-macro.png)

**Batch program**
* Job scheduler que rodará a cada x tempo e ficar ordens de compra e venda com mesmo preço e quantidade e atualizar as carteiras dos usuários.

## Comportamento

![](order-book-flow.png)
