package br.com.atlas.ai.parser;

import br.com.atlas.ai.AIAction;
import br.com.atlas.ai.AIActionType;
import br.com.atlas.ai.AIResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AIResponseParser {

    private final ObjectMapper mapper = new ObjectMapper();


    public AIResponse parse(String rawResponse) {
        /*System.out.println("========== RAW RESPONSE ==========");
        System.out.println(rawResponse);
        System.out.println("==================================");
        System.out.println(rawResponse);
*/
        try {

            // JSON retornado pelo Ollama
            JsonNode ollamaResponse = mapper.readTree(rawResponse);

            /*System.out.println("JSON OLLAMA:");
            System.out.println(ollamaResponse.toPrettyString());*/

            // Texto gerado pelo modelo
            JsonNode responseNode = ollamaResponse.get("response");

            String modelResponse;


            if(responseNode != null){

                // Fluxo antigo: Ollama normal
                modelResponse = responseNode.asText();

            }else{

                // Fluxo streaming: JSON da ATLAS já montado
                modelResponse = rawResponse;

            }
            if (!modelResponse.trim().startsWith("{")) {

                System.out.println("[WARN] IA não retornou JSON. Convertendo resposta.");

                return new AIResponse(
                        modelResponse
                );
            }
            // JSON criado pelo Qwen
            JsonNode atlasJson =
                    mapper.readTree(modelResponse);

            String assistantMessage;

            if (atlasJson.has("assistantMessage")) {
                assistantMessage = atlasJson.get("assistantMessage").asText();
            } else {
                assistantMessage = atlasJson.path("message").asText("Sem resposta da IA.");
            }

            AIResponse response = new AIResponse(assistantMessage);


            JsonNode actions =
                    atlasJson.get("actions");


            if (actions != null && actions.isArray()) {


                for (JsonNode actionNode : actions) {


                    AIAction action =
                            new AIAction(
                                    AIActionType.valueOf(
                                            actionNode
                                                    .get("type")
                                                    .asText()
                                    )
                            );


                    JsonNode parameters =
                            actionNode.get("parameters");


                    if (parameters != null) {

                        parameters.fields()
                                .forEachRemaining(field -> {

                                    action.addParameter(
                                            field.getKey(),
                                            field.getValue()
                                                    .asText()
                                    );

                                });

                    }
                    if (!actionNode.has("type")) {
                        continue;
                    }

                    response.addAction(action);

                }

            }


            return response;


        } catch (Exception e) {

            return new AIResponse(
                    "Erro ao interpretar resposta da IA: "
                            + e.getMessage()
            );

        }

    }

}