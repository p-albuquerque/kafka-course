package br.com.alura.ecommerce.contents;

import java.math.BigDecimal;

public class Order {

    public Order(String userId, String orderId, BigDecimal amount) {
        _userId = userId;
        _orderId = orderId;
        _amount = amount;
    }
    String _userId, _orderId;
    BigDecimal _amount;
}
