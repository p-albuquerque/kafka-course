package br.com.alura.ecommerce;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Criar produtor que ira enviar mensagens
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties());

        // Nova mensagem a ser enviada
        String value = "order1,user001,300";
        ProducerRecord record = new ProducerRecord<>("ECOMMERCE_NEW_ORDER", value, value); // topico onde a mensagem ira ser enviada, chave e valor (ambos preenchido com value)

        // Produtor enviando mensagem
        producer.send(record, (data, ex) -> {
            if (ex != null) {
                ex.printStackTrace();
                return;
            }

            System.out.println(data.topic() + ":::partition" + data.partition() + " / offset " + data.offset());
        }).get();
        /*
            NOTA: O método send não é síncrono (retorna Future), se quiser fazer a execução aguardar, precisa .get()
            Também é possível configurar uma função de callback, exe: avisar que tópico foi criado com sucesso, ou tratar erros
         */
    }

    private static Properties properties() {
        // Kafka producer precisa de algumas propriedades:
        Properties properties = new Properties();

        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092"); // Onde rodar o kafka (bootstrap server 9092)
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName()); // Chave do map KafkaProducer irao transformar String em bytes. Serializador de strings
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName()); // Valor do map KafkaProducer irao transformar String em bytes. Serializador de strings

        return properties;
    }
}
