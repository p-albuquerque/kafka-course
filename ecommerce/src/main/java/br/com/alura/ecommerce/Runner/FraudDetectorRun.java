package br.com.alura.ecommerce;

public class FraudDetectorRun {
    public static void main(String[] args) {
        KafkaService fraudDetectorService = new FraudDetectorService("ECOMMERCE_NEW_ORDER");

        fraudDetectorService.run();
    }
}
