package br.com.alura.ecommerce;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.serialization.Serializer;

public class GsonSerializer<T> implements Serializer<T> {

    @Override
    public byte[] serialize(String s, T object) {
        // Pegar o value da mensagem (object), transforma em json atraves do gson lib e traduz este json em bytes
        return gson.toJson(object).getBytes();
    }

    private final Gson gson = new GsonBuilder().create();
}
