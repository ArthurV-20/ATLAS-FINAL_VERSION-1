package br.com.atlas.ai.voice;

import br.com.atlas.ai.AIManager;
import br.com.atlas.ai.AIModel;
import br.com.atlas.ai.AIRequest;
import br.com.atlas.ai.AIResponse;
import br.com.atlas.ai.voice.audio.AudioRecorder;
import br.com.atlas.ai.voice.audio.AudioStream;
import br.com.atlas.ai.voice.engine.SpeechToTextEngine;
import br.com.atlas.ai.voice.state.AtlasState;
import br.com.atlas.ai.voice.state.AtlasStateManager;
import br.com.atlas.ai.voice.wakeword.WakeWordDetector;

import java.io.File;

public class VoiceController {

    private final WakeWordDetector wakeWordDetector;
    private final AtlasStateManager stateManager;
    private final AudioRecorder audioRecorder;
    private final SpeechToTextEngine speechToTextEngine;
    private final AudioStream audioStream;
    private final AIManager aiManager;

    public VoiceController(
            WakeWordDetector wakeWordDetector,
            AtlasStateManager stateManager,
            AudioRecorder audioRecorder,
            SpeechToTextEngine speechToTextEngine,
            AIManager aiManager,
            AudioStream audioStream
    ) {

        this.wakeWordDetector = wakeWordDetector;
        this.stateManager = stateManager;
        this.audioRecorder = audioRecorder;
        this.speechToTextEngine = speechToTextEngine;
        this.audioStream = audioStream;
        this.aiManager = aiManager;
    }

    public VoiceResult processAudio(byte[] audio) {

        if (stateManager.getState() != AtlasState.IDLE) {
            return null;
        }

        if (wakeWordDetector.detect(audio)) {

            stateManager.changeState(
                    AtlasState.LISTENING
            );

            return onWakeWordDetected();
        }

        return null;
    }

    private VoiceResult onWakeWordDetected() {

        System.out.println("ATLAS ativada!");

        try {

            String command =
                    captureCommand();

            if (command == null || command.isBlank()) {

                System.out.println(
                        "Nenhum comando reconhecido."
                );

                stateManager.changeState(
                        AtlasState.IDLE
                );

                return null;
            }

            AIResponse response =
                    processCommand(command);

            stateManager.changeState(
                    AtlasState.IDLE
            );

            return new VoiceResult(
                    command,
                    response
            );

        } catch (Exception e) {

            e.printStackTrace();

            stateManager.changeState(
                    AtlasState.IDLE
            );

            return null;

        }

    }

    private String captureCommand() throws Exception {

        File audioFile =
                audioRecorder.record();

        System.out.println(
                "Arquivo criado: "
                        + audioFile.getAbsolutePath()
        );

        String text =
                speechToTextEngine.transcribe(
                        audioFile
                );

        text = text
                .replace("\\n", "")
                .trim();

        System.out.println(
                "Usuário disse: " + text
        );

        return text;
    }

    private AIResponse processCommand(
            String command
    ) throws Exception {

        AIRequest request =
                new AIRequest(
                        command,
                        command,
                        AIModel.GENERAL
                );

        return aiManager.process(request);

    }

    public VoiceResult listen() throws Exception {

        audioStream.start();

        while(true){

            byte[] audio =
                    audioStream.readChunk();


            VoiceResult result =
                    processAudio(audio);


            if(result != null){

                audioStream.stop();

                return result;

            }

        }

    }
}