package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class EmailService {
    public static void main(String[] args) {
        // Criar consumidor que ira ouvir as mensagens
        KafkaConsumer consumer = new KafkaConsumer<String, String>(properties());

        // Inscrever o consumer em um tópico, ou seja, toda nova mensagem enviada neste tópico disparará este serviço
        consumer.subscribe(Collections.singletonList("ECOMMERCE_SEND_EMAIL"));

        // Manter o consumer escutando em loop
        while(true) {
            // Verificar se aquele tópico contém alguma nova mensagem enviada, durante um determinado tempo
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

            // Verificar se, durante essa "rodada" de escuta, chegou alguma mensagem
            if (!records.isEmpty()) {
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("#########33############################################################");
                    System.out.println("Enviando email com a mensagem: " + record.key());
                }
            }

        }
    }

    private static Properties properties() {
        // Kafka producer precisa de algumas propriedades:
        Properties properties = new Properties();

        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092"); // Onde escutar o kafka (bootstrap server 9092)
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName()); // Chave do map KafkaConsumer irao transformar de bytes para String.
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName()); // Valor do map KafkaConsumer irao transformar de bytes para String.
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, EmailService.class.getSimpleName()); // Define o escopo de cada mensagem
        /*
            Se apenas um serviço está contido neste group (oq é o caso agora), então este serviço receberá todas as
            mensagens deste tópico. Se um outro serviço estiver neste mesmo group, então as mensagens serão distribuídas
            entre ambos, ou seja, a mensagem que um serviço pegou, o outro não pegará.
        */


        return properties;
    }
}
