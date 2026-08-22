package br.com.atlas.ai;

import br.com.atlas.ai.memory.MemoryQueryDetector;
import br.com.atlas.ai.memory.MemoryQueryType;
import br.com.atlas.ai.memory.longterm.MemoryEntry;
import br.com.atlas.ai.memory.longterm.MemoryExtractor;
import br.com.atlas.ai.memory.longterm.MemoryManager;
import br.com.atlas.ai.memory.longterm.MemoryRetriever;
import br.com.atlas.ai.preprocessing.TextNormalizer;
import br.com.atlas.ai.providers.LocalAIProvider;
import br.com.atlas.ai.router.IntentRouter;
import br.com.atlas.ai.router.IntentType;
import br.com.atlas.ai.local.LocalResponseManager;
import br.com.atlas.ai.client.StreamListener;
import br.com.atlas.tool.ToolManager;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;


public class AIManager {

    private final AIRouter aiRouter;

    private final MemoryManager memoryManager;

    private final MemoryExtractor memoryExtractor;

    private final MemoryRetriever memoryRetriever =
            new MemoryRetriever();

    private final IntentRouter intentRouter =
            new IntentRouter();

    private final TextNormalizer textNormalizer =
            new TextNormalizer();

    private final LocalResponseManager localResponseManager =
            new LocalResponseManager();

    private final MemoryQueryDetector memoryQueryDetector =
            new MemoryQueryDetector();
    private final ToolManager toolManager =
            new ToolManager();

    public AIManager() {

        this.aiRouter =
                new AIRouter();

        this.memoryManager =
                new MemoryManager();

        this.memoryExtractor =
                new MemoryExtractor();

    }



    public AIResponse process(
            AIRequest request
    ) throws Exception {


        System.out.println(
                "[DEBUG] Entrou em AIManager.process()"
        );


        String textoNormalizado =
                textNormalizer.normalize(
                        request.getPrompt()
                );


        System.out.println(
                "[TEXTO NORMALIZADO] "
                        + textoNormalizado
        );


        IntentType intent =
                intentRouter.analyze(
                        textoNormalizado
                );
        if(intent == IntentType.NEXUS_ENABLE){

            aiRouter.activateNexus();

            return new AIResponse(
                    "Nexus Protocol ativado."
            );

        }

        if(intent == IntentType.NEXUS_DISABLE){

            aiRouter.deactivateNexus();

            return new AIResponse(
                    "Retornando ao modo padrão."
            );

        }

        //AI ADITION
        AIRequest requestNormalizado =
                new AIRequest(
                        textoNormalizado,
                        request.getUserMessage(),
                        request.getModel()
                );

        requestNormalizado.setIntent(intent);

        AIResponse localResponse =
                localResponseManager.process(
                        intent,
                        requestNormalizado
                );
        //AI ADDITION ENDS HERE


        if(localResponse != null){
            for(AIAction action :
                    localResponse.getActions()) {


                toolManager.execute(action);

            }


            memoryManager.addAssistantMessage(
                    localResponse.getMessage()
            );
            return localResponse;

        }


        System.out.println(
                "[DEBUG INTENT FINAL] "
                        + intent
        );


        request.setIntent(intent);



        // =========================
        // MEMÓRIA CURTA
        // =========================

        memoryManager.addUserMessage(
                request.getPrompt()
        );



        // =========================
        // EXTRAÇÃO DE MEMÓRIA
        // =========================


        if(shouldExtractMemory(
                request.getPrompt()
        )){


            System.out.println(
                    "[MEMORY] Extraindo informação..."
            );


            memoryExtractor.analyze(
                    request.getPrompt(),
                    memoryManager
            );

        }



        // =========================
        // RECUPERAÇÃO DE MEMÓRIA
        // =========================


        List<MemoryEntry> memories =
                List.of();


        MemoryQueryType memoryQuery =
                memoryQueryDetector.detect(
                        request.getPrompt()
                );


        if(memoryQuery != MemoryQueryType.NONE){


            System.out.println(
                    "[MEMORY] Recuperando memória..."
            );

            memories =
                    memoryRetriever.retrieve(
                            memoryQuery,
                            memoryManager.getLongTermMemory()
                    );
            System.out.println("===== MEMÓRIAS RECUPERADAS =====");

            for(MemoryEntry memory : memories){

                System.out.println(
                        memory.getKey()
                                + " = "
                                + memory.getValue()
                );

            }

            System.out.println("===============================");
        }



        // =========================
        // PROMPT
        // =========================


        String promptCompleto =
                PromptBuilder.build(
                        AtlasSystemPrompt.PROMPT,
                        memoryManager.getConversationMemory(),
                        memories,
                        request.getPrompt()
                );



        AIRequest requestComContexto =
                new AIRequest(
                        promptCompleto,
                        request.getUserMessage(),
                        request.getModel()
                );


        requestComContexto.setIntent(
                request.getIntent()
        );



        System.out.println(
                "REQUEST CONTEXTO = "
                        + requestComContexto.getIntent()
        );



        // =========================
        // IA
        // =========================


        long inicioIA =
                System.currentTimeMillis();



        AIResponse response =
                aiRouter.process(
                        requestComContexto
                );



        System.out.println(
                "[TEMPO] ROUTER + OLLAMA: "
                        +
                        (System.currentTimeMillis()
                                - inicioIA)
                        +
                        " ms"
        );



        // =========================
        // AÇÕES
        // =========================


        for (AIAction action : response.getActions()) {

            toolManager.execute(action);

        }



        // =========================
        // MEMÓRIA ASSISTENTE
        // =========================


        memoryManager.addAssistantMessage(
                response.getMessage()
        );



        System.out.println(
                "[DEBUG RESPOSTA FINAL AIManager] "
                        +
                        response.getMessage()
        );


        return response;

    }




    public void processStream(
            AIRequest request,
            StreamListener listener
    ) throws Exception {


        String textoNormalizado =
                textNormalizer.normalize(
                        request.getPrompt()
                );


        IntentType intent =
                intentRouter.analyze(
                        textoNormalizado
                );


        request.setIntent(intent);



        String promptCompleto =
                PromptBuilder.build(
                        AtlasSystemPrompt.PROMPT,
                        memoryManager.getConversationMemory(),
                        List.of(),
                        request.getPrompt()
                );



        AIRequest requestComContexto =
                new AIRequest(
                        promptCompleto,
                        request.getUserMessage(),
                        request.getModel()
                );


        requestComContexto.setIntent(intent);



        StringBuilder respostaCompleta =
                new StringBuilder();



        StreamListener wrapper =
                new StreamListener() {


                    @Override
                    public void onToken(
                            String token
                    ){

                        respostaCompleta.append(
                                token
                        );

                        listener.onToken(
                                token
                        );

                    }



                    @Override
                    public void onComplete(){


                        memoryManager.addAssistantMessage(
                                respostaCompleta.toString()
                        );


                        listener.onComplete();

                    }

                };



        aiRouter.processStream(
                requestComContexto,
                wrapper
        );

    }

    private String normalize(String text) {

        text = Normalizer.normalize(
                text,
                Normalizer.Form.NFD
        );

        text = text.replaceAll("\\p{M}", "");

        return text
                .toLowerCase()
                .replaceAll("[^a-z0-9 ]", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }

    private boolean shouldExtractMemory(
            String text
    ){

        String command =
                text.toLowerCase();


        return command.contains("meu nome é")
                || command.contains("me chamo")
                || command.contains("pode me chamar de")
                || command.contains("minha cor favorita é")
                || command.contains("gosto de programar em")
                || command.contains("minha linguagem favorita é")
                || command.contains("minha linguagem é")
                || command.contains("programo em")
                || command.contains("meu projeto");
    }

    private boolean shouldRetrieveMemory(
            String text
    ){

        String command =
                text.toLowerCase();


        return command.contains("qual meu nome")
                || command.contains("qual é meu nome")
                || command.contains("qual é o meu nome")
                || command.contains("minha cor")
                || command.contains("qual minha cor")
                || command.contains("qual é minha cor")
                || command.contains("meu projeto")
                || command.contains("qual linguagem")
                || command.contains("como falei")
                || command.contains("anteriormente")
                || command.contains("você lembra")
                || command.contains("você lembra meu nome?")
                || command.contains("você lembra meu nome")
                || command.contains("você lembra meu")
                || command.contains("você lembra minha cor?")
                || command.contains("você lembra minha cor favorita?")
                || command.contains("você lembra quais projetos")
                || command.contains("você lembra dos meu projetos?")
                || command.contains("você lembra dos meus projetos")
                || command.contains("você lembra os meus projetos")
                || command.contains("você lembra os projetos que tenho?")
                || command.contains("você lembra minha cor favorita");

    }
}