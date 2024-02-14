package br.com.alura.ecommerce;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Feedback do envio das mensagens
        Callback callback = (data, ex) -> {
            if (ex != null) {
                ex.printStackTrace();
                return;
            }
            System.out.println("sucesso enviando " + data.topic() + ":::partition " + data.partition() + "/ offset " + data.offset());
        };

        // Preparando produtor e usando-o para enviar as mensagens dos seus respectivos tópicos
        KafkaProducer producer = new KafkaProducer<String, String>(properties());

        // Enviar 100 mensagens para _NEW_ORDER randomizando as partições que receberão
        for(int i = 0; i < 100; i++) {
            // Preparando mensagem para tópico NEW_ORDER
            String key = UUID.randomUUID().toString();
            String value = "item#" + i;

            ProducerRecord newOrderRecord = new ProducerRecord<>("ECOMMERCE_NEW_ORDER", key, value);
            producer.send(newOrderRecord, callback).get();
        }

        // Preparando mensagem para tópico _SEND_EMAIL
        String email = "Seu pedido foi submetido, iniciando análise, aguardando confirmação!";

        ProducerRecord emailRecord = new ProducerRecord<>("ECOMMERCE_SEND_EMAIL", "Email", email);

        producer.send(emailRecord, callback).get();
    }

    private static Properties properties() {
        Properties properties = new Properties();

        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        return properties;
    }
}
