package br.com.alura.ecommerce.services;

import br.com.alura.ecommerce.services.KafkaService;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class FraudDetectorService extends KafkaService {

    public FraudDetectorService(String topic) {
        super(FraudDetectorService.class.getSimpleName(), topic);
    }

    @Override
    protected void consume(ConsumerRecord<String, String> record) {
        System.out.println("------------------------------------------");
        System.out.println("Checando se há fraude no pedido");
        System.out.println("Key :: " + record.key());
        System.out.println("Value :: " + record.value());
        System.out.println("Partition :: " + record.partition());
        System.out.println("offset :: " + record.offset());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Checagem finalizada");
    }
}
