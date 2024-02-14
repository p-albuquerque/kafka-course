package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class EmailService extends KafkaService {

    public EmailService(String topic) {
        super(EmailService.class.getSimpleName(), topic);
    }

    @Override
    protected void consume(ConsumerRecord<String, String> record) {
        System.out.println("#####################################################");
        System.out.println("Enviando Email!!!");
        System.out.println("Key :: " + record.key());
        System.out.println("Value :: " + record.value());
        System.out.println("Partition :: " + record.partition());
        System.out.println("offset :: " + record.offset());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Email Enviado");
    }
}
