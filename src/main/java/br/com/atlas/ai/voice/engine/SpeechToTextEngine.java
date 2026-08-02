package br.com.atlas.ai.voice.engine;

import java.io.File;

public interface SpeechToTextEngine {


    String transcribe(
            File audio
    ) throws Exception;


}