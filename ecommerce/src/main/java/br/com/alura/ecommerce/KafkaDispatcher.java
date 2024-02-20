package br.com.alura.ecommerce;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.Closeable;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class KafkaDispatcher<T> implements Closeable {

    private final KafkaProducer<String, T> _producer;

    public KafkaDispatcher () {
        this._producer = new KafkaProducer<>(properties());
    }

    public void send(String key, T value, String topic) throws ExecutionException, InterruptedException {
        // Feedback do envio das mensagens
        Callback callback = (data, ex) -> {
            if (ex != null) {
                ex.printStackTrace();
                return;
            }
            System.out.println("sucesso enviando " + data.topic() + ":::partition " + data.partition() + "/ offset " + data.offset());
        };

        // Enviar 100 mensagens para _NEW_ORDER randomizando as partições que receberão
        ProducerRecord newOrderRecord = new ProducerRecord<>(topic, key, value);
        _producer.send(newOrderRecord, callback).get();

    }

    private static Properties properties() {
        Properties properties = new Properties();

        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, GsonSerializer.class.getName());

        return properties;
    }

    @Override
    public void close() {
        this._producer.close();
    }
}
