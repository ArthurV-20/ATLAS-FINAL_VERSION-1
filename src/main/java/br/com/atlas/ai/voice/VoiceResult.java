package br.com.atlas.ai.voice;

import br.com.atlas.ai.AIResponse;

public class VoiceResult {

    private final String transcription;
    private final AIResponse response;

    public VoiceResult(
            String transcription,
            AIResponse response
    ) {

        this.transcription = transcription;
        this.response = response;

    }

    public String getTranscription() {

        return transcription;

    }

    public AIResponse getResponse() {

        return response;

    }

}