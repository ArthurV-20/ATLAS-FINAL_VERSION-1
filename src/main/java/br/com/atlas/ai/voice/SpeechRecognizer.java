package br.com.atlas.ai.voice;

import br.com.atlas.ai.voice.provider.SpeechProvider;

public class SpeechRecognizer {

    private final SpeechProvider provider;

    public SpeechRecognizer(SpeechProvider provider) {

        this.provider = provider;

    }

    public String listen() throws Exception {

        return provider.listen();

    }

}