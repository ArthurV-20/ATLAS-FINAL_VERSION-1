package br.com.atlas.ai.voice;


import br.com.atlas.ai.voice.audio.AudioRecorder;
import br.com.atlas.ai.voice.audio.AudioStream;
import br.com.atlas.ai.voice.engine.impl.WhisperServerSpeechToTextEngine;
import br.com.atlas.ai.voice.state.AtlasStateManager;
import br.com.atlas.ai.voice.wakeword.impl.SimpleWakeWordDetector;


public class VoiceTest {


    public static void main(String[] args) throws Exception {


        AudioStream audioStream =
                new AudioStream();


        VoiceController controller =
                new VoiceController(
                        new SimpleWakeWordDetector(),
                        new AtlasStateManager(),
                        new AudioRecorder(),
                        new WhisperServerSpeechToTextEngine(
                                "http://127.0.0.1:8080"
                        ),
                        new br.com.atlas.ai.AIManager(),
                        audioStream
                );


        while(true){

            VoiceResult result =
                    controller.listen();


            if(result != null){

                System.out.println(
                        "=========================="
                );

                System.out.println(
                        "Comando reconhecido: "
                                + result.getTranscription()
                );


                System.out.println(
                        "=========================="
                );

            }

        }

    }

}