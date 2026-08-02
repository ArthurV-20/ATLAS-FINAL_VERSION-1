package br.com.atlas.ai.providers;

import br.com.atlas.ai.AIProvider;
import br.com.atlas.ai.AIRequest;
import br.com.atlas.ai.AIResponse;
import br.com.atlas.ai.client.StreamListener;

public class NexusProvider implements AIProvider {


    @Override
    public AIResponse processPrompt(AIRequest request) {

        // Aqui futuramente entra o modelo 7B

        return new AIResponse(
                "Resposta do Nexus Protocol."
        );
    }


    @Override
    public void processPromptStream(
            AIRequest request,
            StreamListener listener
    ) throws Exception {

        listener.onToken(
                "Resposta streaming do Nexus."
        );

    }

}