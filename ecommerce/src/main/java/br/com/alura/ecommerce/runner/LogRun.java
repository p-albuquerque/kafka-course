package br.com.alura.ecommerce.runner;

import br.com.alura.ecommerce.services.KafkaService;
import br.com.alura.ecommerce.services.LogService;

import java.util.regex.Pattern;

public class LogRun {
    public static void main(String[] args) {
        KafkaService logService = new LogService(Pattern.compile("ECOMMERCE.*"));

        logService.run();
    }
}
