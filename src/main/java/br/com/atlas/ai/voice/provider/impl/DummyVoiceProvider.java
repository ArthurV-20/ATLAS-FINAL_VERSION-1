package br.com.atlas.ai.voice.provider.impl;

import br.com.atlas.ai.voice.provider.VoiceProvider;


public class DummyVoiceProvider implements VoiceProvider {


    @Override
    public void speak(String text) throws Exception {


        System.out.println(
                "[VOICE OUTPUT] "
                        + text
        );


    }

}