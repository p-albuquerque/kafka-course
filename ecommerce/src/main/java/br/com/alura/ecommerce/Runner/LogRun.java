package br.com.alura.ecommerce;

import java.util.regex.Pattern;

public class LogRun {
    public static void main(String[] args) {
        KafkaService logService = new LogService(Pattern.compile("ECOMMERCE.*"));

        logService.run();
    }
}
