package br.com.alura.ecommerce.runner;

import br.com.alura.ecommerce.services.FraudDetectorService;
import br.com.alura.ecommerce.services.KafkaService;

public class FraudDetectorRun {
    public static void main(String[] args) {
        KafkaService fraudDetectorService = new FraudDetectorService("ECOMMERCE_NEW_ORDER");

        fraudDetectorService.run();
    }
}
