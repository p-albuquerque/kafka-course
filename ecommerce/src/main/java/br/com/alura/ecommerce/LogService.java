package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.sql.Date;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.regex.Pattern;

public class LogService extends KafkaService {
    public LogService(String topic) {
        super(topic);
    }

    @Override
    protected void consume(ConsumerRecord<String, String> record) {
        System.out.println("#########33############################################################");
        System.out.println("Novo registro de " + record.topic());
    }
}
