package br.com.atlas.ai.client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AIClient {

    private final String endpoint;
    private final HttpClient httpClient;

    public AIClient(String endpoint) {
        this.endpoint = endpoint;
        this.httpClient = HttpClient.newHttpClient();
    }


    public String sendPrompt(
            String model,
            String prompt
    ) throws Exception{

        System.out.println("[DEBUG] Entrou em AIClient.sendPrompt()");
        System.out.println(
                "[AIClient] Thread: " + Thread.currentThread().getName()
        );

        String escapedPrompt = escapeJson(prompt);
//o batch é o uso de RAM, aí dá pra usar por exemplo(32,64,128, etc), depende o quanto de RAM o PC tem disponível.
// o thread é os núcelos do CPU. Aqui nem preciso mexer, 6 é o melhor.
//predict é tipo os tokens(quantidade de palavras). E aqui vale a pena analisar como o modelo de 7B responde.

        String json = """
{
  "model":"%s",
  "prompt":"%s",
  "stream":false,
  "keep_alive":"12h",
  "options":{
    "temperature":0.2,
    "num_predict":3000,
    "num_thread":8,
    "num_ctx":1500,
    "num_batch":128
  }
}
""".formatted(model, escapedPrompt);

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(URI.create(endpoint))
                        .header(
                                "Content-Type",
                                "application/json"
                        )
                        .POST(
                                HttpRequest.BodyPublishers.ofString(json)
                        )
                        .build();

        System.out.println("========== PROMPT ENVIADO ==========");
        System.out.println(json);
        System.out.println("====================================");
        long inicioHttp = System.currentTimeMillis();

        HttpResponse<String> response =
                httpClient.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );
        System.out.println(
                "[OLLAMA RESPONSE SIZE] "
                        + response.body().length()
                        + " caracteres"
        );
        System.out.println(
                "[AIClient] HTTP puro: "
                        + (System.currentTimeMillis() - inicioHttp)
                        + " ms"
        );

        return response.body();
    }

    private String escapeJson(String text) {

        return text
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");

    }
    public void streamPrompt(
            String model,
            String prompt,
            StreamListener listener
    ) throws Exception {


        String escapedPrompt = escapeJson(prompt);


        String json = """
{
  "model":"%s",
  "prompt":"%s",
  "stream":true,
  "keep_alive":"1h",
  "options":{
    "temperature":0.2,
    "num_predict":150,
    "num_thread":6,
    "num_ctx":512,
    "num_batch":128
  }
}
""".formatted(model, escapedPrompt);



        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(URI.create(endpoint))
                        .header(
                                "Content-Type",
                                "application/json"
                        )
                        .POST(
                                HttpRequest.BodyPublishers.ofString(json)
                        )
                        .build();



        HttpResponse<java.io.InputStream> response =
                httpClient.send(
                        request,
                        HttpResponse.BodyHandlers.ofInputStream()
                );


        try(
                java.io.BufferedReader reader =
                        new java.io.BufferedReader(
                                new java.io.InputStreamReader(
                                        response.body()
                                )
                        )
        ){

            String linha;


            while((linha = reader.readLine()) != null){


                if(linha.isBlank()){
                    continue;
                }


                System.out.println(
                        "[STREAM JSON RECEBIDO] "
                                + linha
                );


                String token =
                        StreamChunkParser.extractText(linha);


                if(!token.isEmpty()){

                    listener.onToken(token);

                }

            }


        }


        listener.onComplete();

    }
}