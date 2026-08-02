package br.com.atlas.ai.voice.provider;

public interface VoiceProvider {

    // Mantém o comportamento antigo que é o de resposta completa
    void speak(String text) throws Exception;


    // Inicia uma sessão de streaming, e aqui eu deixei pra o novo método
    default void startStream() throws Exception {

    }


    // Recebe pedaços da resposta
    default void speakChunk(String chunk) throws Exception {

    }


    // Finaliza o streaming
    default void endStream() throws Exception {

    }

}