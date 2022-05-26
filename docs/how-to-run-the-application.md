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
`POST book-order/orders`

Request:
```json
{
    "price": 35.00,
    "quantity": 1,
    "user": "alice",
    "orderType": "BID"
}
```

| Campo     |                  Descricão                   |
|-----------|:--------------------------------------------:|
| price     |           preco de compra ou venda           |
| quantity  |       quantidade para compra ou venda        |
| orderType |  `BID` ordem de compra, `ASK`ordem de venda  |
| user      | usuário da ordem de compra ou venda          |

Response: `HTTP 200 OK`

Exemplo:

```shell
curl --location --request POST 'http://localhost:8080/order-book/orders' \
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

`POST order-book/wallets`

Request:
```json
{
    "user": "alice",
    "balance": 500.00
}
```

| Campo    |      Descricão       |
|----------|:--------------------:|
| user     | usuário da carteira  |
| balance  | valor a ser inserido |

Response: `HTTP 200 OK`

Exemplo:
```shell
curl --location --request POST 'http://localhost:8080/order-book/wallets' \
--header 'Content-Type: application/json' \
--data-raw '{
    "user": "alice",
    "balance": 500.00
}'
```


### Consulta saldos da carteira.
`GET order-book/wallets/user`

Response:
```json
{
    "user": "alice",
    "balance": 500.00,
    "vibranium": 100
}
```


| Campo     |                 Descricão                 |
|-----------|:-----------------------------------------:|
| user      |            Usuário da carteira            |
| balance   |       Saldo em dinheiro da carteira       |
| vibranium | mostra quantidade de vibranium do usuário |

Exemplo:
```shell
curl --location --request GET 'http://localhost:8080/order-book/wallets/alice'
```

