package br.com.atlas.test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class OllamaBenchmark {

    public static void main(String[] args) throws Exception {

        HttpClient client = HttpClient.newHttpClient();

        String json = """
        {
            "model": "qwen2.5:7b",
            "prompt": "Olá",
            "stream": false
        }
        """;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:11434/api/generate"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        System.out.println("Enviando requisição para o Ollama...");

        long inicio = System.currentTimeMillis();

        HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );

        long fim = System.currentTimeMillis();

        System.out.println("----------------------------------");
        System.out.println("Tempo: " + (fim - inicio) + " ms");
        System.out.println("----------------------------------");
        System.out.println(response.body());
    }
}