package br.com.atlas.ai.voice;

import br.com.atlas.ai.AIManager;
import br.com.atlas.ai.voice.audio.AudioRecorder;
import br.com.atlas.ai.voice.audio.AudioStream;
import br.com.atlas.ai.voice.engine.SpeechToTextEngine;
import br.com.atlas.ai.voice.engine.impl.WhisperServerSpeechToTextEngine;
import br.com.atlas.ai.voice.provider.impl.PiperVoiceProvider;
import br.com.atlas.ai.voice.provider.impl.RealSpeechProvider;
import br.com.atlas.ai.voice.state.AtlasStateManager;
import br.com.atlas.ai.voice.wakeword.impl.SimpleWakeWordDetector;

public class VoiceFactory {

    private VoiceFactory() {
    }


    public static VoiceManager create() {


        AudioRecorder recorder =
                new AudioRecorder();


        SpeechToTextEngine engine =
                new WhisperServerSpeechToTextEngine(
                        "http://127.0.0.1:8080"
                );


        RealSpeechProvider speechProvider =
                new RealSpeechProvider(
                        recorder,
                        engine
                );


        PiperVoiceProvider voiceProvider =
                new PiperVoiceProvider(
                        "/home/srgamestv/Documentos/ToolsUbuntu/piper-env/bin/piper",
                        "/home/srgamestv/Documentos/ToolsUbuntu/piper-models/pt_BR-faber-medium.onnx"
                );


        SpeechRecognizer recognizer =
                new SpeechRecognizer(
                        speechProvider
                );


        VoiceSynthesizer synthesizer =
                new VoiceSynthesizer(
                        voiceProvider
                );


        return new VoiceManager(
                recognizer,
                synthesizer
        );

    }



    public static VoiceController createController(
            AIManager aiManager
    ) {


        AudioRecorder recorder =
                new AudioRecorder();


        SpeechToTextEngine engine =
                new WhisperServerSpeechToTextEngine(
                        "http://127.0.0.1:8080"
                );


        AudioStream audioStream =
                new AudioStream();



        return new VoiceController(
                new SimpleWakeWordDetector(),
                new AtlasStateManager(),
                recorder,
                engine,
                aiManager,
                audioStream
        );

    }

}