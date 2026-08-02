package br.com.atlas.ai.providers;

import br.com.atlas.ai.*;
import br.com.atlas.ai.client.StreamListener;

public class DummyProvider implements AIProvider {

    @Override
    public AIResponse processPrompt(AIRequest request) {
        System.out.println("Dummy recebeu: " + request.getPrompt());
        String promptTexto = request.getPrompt().toLowerCase();

        AIResponse response = new AIResponse(
                """
                ====================================
                     ATLAS AI - Dummy Provider
                ====================================

                Prompt recebido:
                %s

                Analisando intenção...
                """.formatted(request.getPrompt())
        );


        if (promptTexto.contains("firefox")
                || promptTexto.contains("navegador")) {
            System.out.println("Reconheceu navegador");
            AIAction action = new AIAction(
                    AIActionType.OPEN_APPLICATION
            );

            action.addParameter(
                    AIParameters.APPLICATION,
                    "firefox"
            );

            response.addAction(action);
        }


        if (promptTexto.contains("terminal")
                || promptTexto.contains("console")) {

            AIAction action = new AIAction(
                    AIActionType.OPEN_APPLICATION
            );

            action.addParameter(
                    AIParameters.APPLICATION,
                    "gnome-terminal"
            );

            response.addAction(action);
        }


        if (promptTexto.contains("intellij")
                || promptTexto.contains("ide")) {

            AIAction action = new AIAction(
                    AIActionType.OPEN_APPLICATION
            );

            action.addParameter(
                    AIParameters.APPLICATION,
                    "idea"
            );

            response.addAction(action);
            System.out.println("AÇÃO CRIADA: " + action.getType());
        }

        System.out.println("Quantidade de ações: " + response.getActions().size());
        return response;
    }
    @Override
    public void processPromptStream(
            AIRequest request,
            StreamListener listener
    ) throws Exception {

        listener.onToken("Streaming não implementado.");

        listener.onComplete();
    }
}