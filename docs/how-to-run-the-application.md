## Como executar Local
Pré-requisitos instalados:
- Docker
- Docker Compose

1. Fazer o clone do projeto.
2. Na raiz executar `sh build.sh`.
3. Executar `docker-compose up -d`

## Endpoints

## order

### Criar uma ordem de compra ou venda.
`POST book-order/order`

Request:
```json
{
    "price": 35.00,
    "quantity": 1,
    "user": "alice",
    "orderType": "BID"
}
```

price: preco de compra ou venda.

quantity: quantidade para compra ou venda.

orderType: `BID` ordem de compra, `ASK`ordem de venda.

user: usuário da ordem de compra ou venda.

Response: `200 OK`

Exemplo:

```shell
curl --location --request POST 'http://localhost:8080/order-book/order' \
--header 'Content-Type: application/json' \
--data-raw '{
    "price": 35.00,
    "quantity": 1,
    "user": "bob",
    "orderType": "BID"
}'
```

## wallet
### Adiciona dinheiro na carteira do usuário.

`POST order-book/wallet`

Request:
```json
{
    "user": "alice",
    "balance": 500.00
}
```
user: usuário da carteira.

balance: valor a ser inserido.

Exemplo:
```shell
curl --location --request POST 'http://localhost:8080/order-book/wallet' \
--header 'Content-Type: application/json' \
--data-raw '{
    "user": "alice",
    "balance": 500.00
}'
```


### Consulta saldos da carteira.
`GET order-book/wallet/user`

```json
{
    "user": "alice",
    "balance": 500.00,
    "vibranium": 100
}
```

user: Usuário da carteira.

balance: Saldo em dinheiro da carteira.

vibranium: mostra quantidade de vibranium do usuário.

Exemplo:
```shell
curl --location --request GET 'http://localhost:8080/order-book/wallet/alice'
```

