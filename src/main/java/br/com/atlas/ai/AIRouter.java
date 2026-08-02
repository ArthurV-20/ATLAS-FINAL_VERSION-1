package br.com.atlas.ai;

import br.com.atlas.ai.client.StreamListener;
import br.com.atlas.ai.providers.DummyProvider;
import br.com.atlas.ai.providers.LocalAIProvider;
import br.com.atlas.ai.providers.NexusProvider;

import java.util.HashMap;
import java.util.Map;


public class AIRouter {


    private final Map<AIModel, AIProvider> providers =
            new HashMap<>();


    private final AIProvider nexusProvider;


    private AtlasMode mode =
            AtlasMode.STANDARD;



    public AIRouter() {

        System.out.println(
                "[DEBUG] AIRouter iniciado"
        );


        providers.put(
                AIModel.GENERAL,
                new LocalAIProvider()
        );


        providers.put(
                AIModel.DEVELOPMENT,
                new DummyProvider()
        );


        nexusProvider =
                new NexusProvider();

    }



    public AIResponse process(
            AIRequest request
    ){

        AIProvider provider;


        if(mode == AtlasMode.NEXUS){

            provider = nexusProvider;


        } else {


            AIModel model =
                    request.getModel();


            provider =
                    providers.get(model);

        }



        if(provider == null){

            return new AIResponse(
                    "Nenhum provedor encontrado."
            );

        }


        return provider.processPrompt(request);

    }



    public void activateNexus(){

        AIProvider provider =
                providers.get(AIModel.GENERAL);


        if(provider instanceof LocalAIProvider local){

            local.activateNexus();

        }

    }



    public void deactivateNexus(){

        AIProvider provider =
                providers.get(AIModel.GENERAL);


        if(provider instanceof LocalAIProvider local){

            local.deactivateNexus();

        }

    }



    public void processStream(
            AIRequest request,
            StreamListener listener
    ) throws Exception {


        AIProvider provider;


        if(mode == AtlasMode.NEXUS){

            provider = nexusProvider;

        } else {

            provider =
                    providers.get(
                            request.getModel()
                    );

        }



        if(provider == null){

            throw new IllegalArgumentException(
                    "Nenhum provedor encontrado."
            );

        }


        provider.processPromptStream(
                request,
                listener
        );

    }

}