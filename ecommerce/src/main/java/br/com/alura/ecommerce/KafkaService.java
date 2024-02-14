package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public abstract class KafkaService {
    public KafkaService(String classSimpleName, String topic) {
        this._kafkaConsumer = new KafkaConsumer<String, String>(properties(classSimpleName));
        _kafkaConsumer.subscribe(Collections.singletonList(topic));
    }

    public void run() {
        while(true) {
            ConsumerRecords<String, String> records = _kafkaConsumer.poll(Duration.ofMillis(100));

            if (!records.isEmpty()) {

                for (ConsumerRecord<String, String> record : records) {
                    consume(record);
                }
            }

        }
    }

    protected abstract void consume(ConsumerRecord<String, String> record);

    protected static Properties properties(String classSimpleName) {
        Properties properties = new Properties();

        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, classSimpleName);

        return properties;
    }



    private final KafkaConsumer<String, String> _kafkaConsumer;
}
