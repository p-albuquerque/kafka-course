package br.com.alura.ecommerce;

import br.com.alura.ecommerce.contents.Order;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try (KafkaDispatcher<Order> orderDispatcher = new KafkaDispatcher<>()) {
            try (KafkaDispatcher<String> emailDispatcher = new KafkaDispatcher<>()) {

                // Enviar 100 mensagens para _NEW_ORDER randomizando as partições que receberão
                for (int i = 0; i < 100; i++) {
                    // Preparando mensagem para tópico NEW_ORDER
                    String userId = "user##" + UUID.randomUUID();
                    String orderId = String.format("item%s##%s", i, UUID.randomUUID());
                    BigDecimal amount = new BigDecimal(Math.random() * 5000 + 1);
                    Order order = new Order(userId, orderId, amount);

                    orderDispatcher.send(userId, order, "ECOMMERCE_SEND_EMAIL");
                }

                // Preparando mensagem para tópico _SEND_EMAIL
                String email = "Seu pedido foi submetido, iniciando análise, aguardando confirmação!";

                emailDispatcher.send("Novo Email", email, "ECOMMERCE_NEW_ORDER");
            }
        }
    }
}
