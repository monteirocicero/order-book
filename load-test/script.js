import http from 'k6/http';

export default function () {
  const url = 'http://localhost:8080/order-book/order';

    var min = 34.00,
        max = 35.00,
        price_random = Math.random() * (max - min) + min;

    var random_number = Math.floor(Math.random() * 2);
    var user_random = "bob";
    var order_type_random = "ASK";

    if (random_number % 2 == 0) {
      user_random = "alice";
      order_type_random = "BID";
    } 

  const payload = JSON.stringify({
    price: price_random,
    quantity: 10,
    user: user_random,
    orderType: order_type_random
  });

  const params = {
    headers: {
      'Content-Type': 'application/json',
    },
  };

  http.post(url, payload, params);
}
