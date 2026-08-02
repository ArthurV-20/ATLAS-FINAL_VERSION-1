package br.com.atlas.ai.voice.provider.impl;

import br.com.atlas.ai.voice.provider.SpeechProvider;


public class DummySpeechProvider implements SpeechProvider {


    @Override
    public String listen() throws Exception {

        System.out.println(
                "[VOICE] Simulando entrada de microfone..."
        );


        return "Olá ATLAS, meu nome é Arthur";

    }

}