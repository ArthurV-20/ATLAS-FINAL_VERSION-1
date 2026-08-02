package br.com.atlas.ai.voice;

import br.com.atlas.ai.voice.provider.VoiceProvider;

public class VoiceSynthesizer {

    private final VoiceProvider provider;

    public VoiceSynthesizer(VoiceProvider provider) {

        this.provider = provider;

    }

    public void speak(String text) throws Exception {

        provider.speak(text);

    }

}