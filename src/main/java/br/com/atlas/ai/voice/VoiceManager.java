package br.com.atlas.ai.voice;

public class VoiceManager {

    private final SpeechRecognizer speechRecognizer;
    private final VoiceSynthesizer voiceSynthesizer;


    public VoiceManager(
            SpeechRecognizer speechRecognizer,
            VoiceSynthesizer voiceSynthesizer
    ) {

        this.speechRecognizer = speechRecognizer;
        this.voiceSynthesizer = voiceSynthesizer;

    }


    public String listen() throws Exception {
        System.out.println("[DEBUG] VoiceManager.listen() chamado");

        return speechRecognizer.listen();

    }


    public void speak(String text) throws Exception {

        voiceSynthesizer.speak(text);

    }
    public void speakStream(
            String text
    ) throws Exception {

        voiceSynthesizer.speak(text);

    }

}