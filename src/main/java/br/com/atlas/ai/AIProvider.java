package br.com.atlas.ai;

import br.com.atlas.ai.client.StreamListener;

public interface AIProvider {

    AIResponse processPrompt(AIRequest request);

    void processPromptStream(
            AIRequest request,
            StreamListener listener
    ) throws Exception;

}