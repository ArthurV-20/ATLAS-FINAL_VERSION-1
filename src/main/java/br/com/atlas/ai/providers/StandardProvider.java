package br.com.atlas.ai.providers;

import br.com.atlas.ai.AIProvider;
import br.com.atlas.ai.AIRequest;
import br.com.atlas.ai.AIResponse;
import br.com.atlas.ai.client.StreamListener;

public class StandardProvider implements AIProvider {


    @Override
    public AIResponse processPrompt(AIRequest request) {

        // Aqui futuramente entra o modelo 1.5B

        return new AIResponse(
                "Resposta do modelo padrão."
        );
    }


    @Override
    public void processPromptStream(
            AIRequest request,
            StreamListener listener
    ) throws Exception {

        listener.onToken(
                "Resposta streaming do modelo padrão."
        );

    }

}