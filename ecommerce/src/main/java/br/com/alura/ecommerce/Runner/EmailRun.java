package br.com.alura.ecommerce;

public class EmailRun {
    public static void main(String[] args) {
        KafkaService emailService = new EmailService("ECOMMERCE_SEND_EMAIL");

        emailService.run();
    }
}
