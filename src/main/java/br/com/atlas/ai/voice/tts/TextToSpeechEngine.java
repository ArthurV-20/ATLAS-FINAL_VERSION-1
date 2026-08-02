package br.com.atlas.ai.voice.tts;


public interface TextToSpeechEngine {


    void speak(
            String text
    ) throws Exception;


}