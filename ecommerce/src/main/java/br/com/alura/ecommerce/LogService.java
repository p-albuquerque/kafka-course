package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class LogService extends KafkaService {

    public LogService(String topic) {
        super(LogService.class.getSimpleName(), topic);
    }

    @Override
    protected void consume(ConsumerRecord<String, String> record) {
        System.out.println("------------------------------------------");
        System.out.println("LOG: " + record.topic());
        System.out.println("Key :: " + record.key());
        System.out.println("Value :: " + record.value());
        System.out.println("Partition :: " + record.partition());
        System.out.println("offset :: " + record.offset());
    }
}
