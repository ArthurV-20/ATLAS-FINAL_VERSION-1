package br.com.atlas.ai.streaming;

import br.com.atlas.ai.client.AIClient;
import br.com.atlas.ai.client.StreamListener;

public class StreamManager {


    private final AIClient client;


    public StreamManager(AIClient client){

        this.client = client;

    }



    public void startStream(
            String model,
            String prompt,
            StreamListener listener
    ) throws Exception {


        client.streamPrompt(
                model,
                prompt,
                listener
        );

    }

}