package br.com.alura.ecommerce.runner;

import br.com.alura.ecommerce.services.EmailService;
import br.com.alura.ecommerce.services.KafkaService;

public class EmailRun {
    public static void main(String[] args) {
        KafkaService emailService = new EmailService("ECOMMERCE_SEND_EMAIL");

        emailService.run();
    }
}
