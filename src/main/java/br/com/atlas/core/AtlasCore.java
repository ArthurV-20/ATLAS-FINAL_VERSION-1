package br.com.atlas.core;

import br.com.atlas.ai.*;
import br.com.atlas.ai.voice.VoiceController;
import br.com.atlas.ai.voice.VoiceManager;
import br.com.atlas.ai.voice.VoiceResult;

import java.util.ArrayList;
import java.util.List;

public class AtlasCore {

    private final List<String> appsAbertos =
            new ArrayList<>();

    private final AIManager aiManager;
    private final VoiceManager voiceManager;
    private final VoiceController voiceController;


    public AtlasCore(
            AIManager aiManager,
            VoiceManager voiceManager,
            VoiceController voiceController
    ) {

        this.aiManager = aiManager;
        this.voiceManager = voiceManager;
        this.voiceController = voiceController;

    }



    public AIResponse executarComando(
            String comando
    ) throws Exception {


        if (comando == null || comando.isBlank()) {

            throw new IllegalArgumentException(
                    "O comando não pode estar vazio, senhor."
            );

        }


        AIRequest request =
                new AIRequest(
                        comando,
                        comando,
                        AIModel.GENERAL
                );


        return aiManager.process(
                request
        );

    }



    // =========================
    // SISTEMA DE VOZ
    // =========================
    public VoiceResult iniciarModoVoz() throws Exception {

        VoiceResult voiceResult =
                voiceController.listen();

        if (voiceResult == null) {
            throw new Exception(
                    "Nenhum comando reconhecido."
            );
        }

        AIResponse response =
                executarComando(
                        voiceResult.getTranscription()
                );

        voiceManager.speak(
                response.getMessage()
        );

        return voiceResult;
    }

    public int getQuantidadeAppsAbertos(){

        return appsAbertos.size();

    }

}