package br.com.alura.ecommerce;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try (KafkaDispatcher kafkaDispatcher = new KafkaDispatcher()) {

            // Enviar 100 mensagens para _NEW_ORDER randomizando as partições que receberão
            for (int i = 0; i < 100; i++) {
                // Preparando mensagem para tópico NEW_ORDER
                String key = UUID.randomUUID().toString();
                String value = "item#" + i;

                kafkaDispatcher.send(key, value, "ECOMMERCE_SEND_EMAIL");
            }

            // Preparando mensagem para tópico _SEND_EMAIL
            String email = "Seu pedido foi submetido, iniciando análise, aguardando confirmação!";

            kafkaDispatcher.send("Novo Email", email, "ECOMMERCE_NEW_ORDER");
        }
    }
}
