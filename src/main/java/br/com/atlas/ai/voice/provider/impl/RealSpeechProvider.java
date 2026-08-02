package br.com.atlas.ai.voice.provider.impl;


import br.com.atlas.ai.voice.audio.AudioRecorder;
import br.com.atlas.ai.voice.engine.SpeechToTextEngine;
import br.com.atlas.ai.voice.provider.SpeechProvider;

import java.io.File;


public class RealSpeechProvider implements SpeechProvider {


    private final AudioRecorder recorder;
    private final SpeechToTextEngine engine;



    public RealSpeechProvider(
            AudioRecorder recorder,
            SpeechToTextEngine engine
    ){

        this.recorder = recorder;
        this.engine = engine;

    }



    @Override
    public String listen()
            throws Exception {
        System.out.println("[DEBUG] RealSpeechProvider.listen()");

        File audio =
                recorder.record();

        return engine.transcribe(audio);

    }

}