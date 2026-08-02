package br.com.atlas.ai.providers;

import br.com.atlas.ai.*;
import br.com.atlas.ai.client.AIClient;
import br.com.atlas.ai.client.StreamListener;
import br.com.atlas.ai.parser.AIResponseParser;
import br.com.atlas.ai.router.AIModelRouter;
import br.com.atlas.ai.router.IntentType;
import br.com.atlas.ai.router.AIModelType;
import br.com.atlas.ai.streaming.AtlasStreamListener;

public class LocalAIProvider implements AIProvider {

    private final AIClient client;
    private final AIResponseParser parser = new AIResponseParser();
    private final AIModelRouter modelRouter =
            new AIModelRouter();
    private boolean nexusMode = false;

    public LocalAIProvider() {
        this.client = new AIClient(
                "http://localhost:11434/api/generate"
        );
    }
    @Override
    public AIResponse processPrompt(AIRequest request) {

        System.out.println("[DEBUG] Entrou em LocalAIProvider.processPrompt()");
        System.out.println("[INTENT Provider] " + request.getIntent());
        if(request.getIntent() == IntentType.GREETING) {

            int hora = java.time.LocalTime.now().getHour();

            String saudacao;

            /*if (hora < 9) {
                saudacao = "Bom dia, Senhor. Tão cedo senhor??";
            } else if ( hora < 12){
                saudacao = "Hoje vai ser muito produtivo. Bom dia, Senhor.";
            } else if (hora < 18) {
                saudacao = "Boa tarde, Senhor. Já tomou aquele cafézinho, senhor??";
            } else if (hora < 21) {
                saudacao = "Boa noite, Senhor. Como você está??";
            } else {
                saudacao = "Boa noite senhor. Você deveria descansar!";
            }

            return new AIResponse(
                    saudacao

            );
*/
        }

        try {

            long inicio = System.currentTimeMillis();

            // AQUI eu peguei o método privado, que no caso é o choosemodel
            String model = chooseModel(request);
            //TERMINA AQUI
            System.out.println("========== PROMPT RECEBIDO PELO LOCAL AI ==========");
            System.out.println(request.getPrompt());
            System.out.println("==================================================");System.out.println("[MODELO] " + model);
            String respostaIA =
                    client.sendPrompt(
                            model,
                            request.getPrompt()
                    );
            /*AtlasStreamListener listener =
                    new AtlasStreamListener();

            listener.start();

            processPromptStream(
                    request,
                    listener
            );

            String respostaIA =
                    listener.getResponse();*/

            //TERMINA AQUI=====================================
            System.out.println("========== RESPOSTA BRUTA OLLAMA ==========");
            System.out.println(respostaIA);
            System.out.println("===========================================");System.out.println(
                    "[TEMPO] HTTP OLLAMA: "
                            + (System.currentTimeMillis() - inicio)
                            + " ms"
            );

            inicio = System.currentTimeMillis();

            AIResponse response =
                    parser.parse(respostaIA);

            System.out.println(
                    "[TEMPO] PARSER: "
                            + (System.currentTimeMillis() - inicio)
                            + " ms"
            );

            return response;

        } catch (Exception e) {

            e.printStackTrace();

            return new AIResponse(
                    "Erro ao conectar com IA local: "
                            + e.getMessage()
            );

        }
    }

    public void processPromptStream(
            AIRequest request,
            StreamListener listener
    ) throws Exception {

// AQUI eu peguei o método privado, que no caso é o choosemodel
        String model = chooseModel(request);
//TERMINA AQUI
        client.streamPrompt(
                model,
                request.getPrompt(),
                listener
        );
    }
    //ALTERAÇÃO DE MODELOS
    private String chooseModel(AIRequest request) {
        System.out.println("[NEXUS] " + nexusMode);

        if(nexusMode){
                    //ou o de 7B
            return "qwen2.5:7b";

        }


        return "qwen2.5:3b";

    }
    public void activateNexus(){

        nexusMode = true;

    }


    public void deactivateNexus(){

        nexusMode = false;

    }
}
