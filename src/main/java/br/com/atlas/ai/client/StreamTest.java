package br.com.atlas.ai.client;


import br.com.atlas.ai.client.AIClient;
import br.com.atlas.ai.streaming.AtlasStreamListener;
import br.com.atlas.ai.streaming.StreamManager;

public class StreamTest {

    public static void main(String[] args) throws Exception {


        AIClient client =
                new AIClient(
                        "http://localhost:11434/api/generate"
                );


        StreamManager manager =
                new StreamManager(client);


        AtlasStreamListener listener =
                new AtlasStreamListener();


        manager.startStream(
                "qwen2.5:1.5b",
                "Qual a capital do Brasil?",
                listener
        );


        System.out.println(
                "\n\nRESPOSTA COMPLETA:"
        );


        System.out.println(
                listener.getResponse()
        );

    }

}