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
    /*
        Extraindo código em comum da rotina de cada Consumer para esta classe.
        Cada classe específica de Service deverá implementar o metodo "consume()"
        com o comportamento específico de seu processo
    */

    public KafkaService(String topic) {
        this._consumer = new KafkaConsumer<String, String>(properties());
        _consumer.subscribe(Collections.singletonList(topic));
    }

    public void run() {
        while(true) {
            ConsumerRecords<String, String> records = _consumer.poll(Duration.ofMillis(100));

            if (!records.isEmpty()) {
                System.out.println("------: " + records.count() + " Registros encontrados -------------");

                for (ConsumerRecord<String, String> record : records) {
                    consume(record);
                }
            }
        }
    }

    protected abstract void consume(ConsumerRecord<String, String> record);

    private static Properties properties() {
        // Kafka producer precisa de algumas propriedades:
        Properties properties = new Properties();

        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092"); // Onde escutar o kafka (bootstrap server 9092)
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName()); // Chave do map KafkaConsumer irao transformar de bytes para String.
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName()); // Valor do map KafkaConsumer irao transformar de bytes para String.
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, FraudDetectorService.class.getSimpleName()); // Define o escopo de cada mensagem

        /*
            Se apenas um serviço está contido neste group (oq é o caso agora), então este serviço receberá todas as
            mensagens deste tópico. Se um outro serviço estiver neste mesmo group, então as mensagens serão distribuídas
            entre ambos, ou seja, a mensagem que um serviço pegou, o outro não pegará.
        */


        return properties;
    }

    protected final KafkaConsumer<String, String> _consumer;
}
