package br.com.atlas.ai.voice.wakeword.impl;

import br.com.atlas.ai.voice.wakeword.WakeWordDetector;

public class SimpleWakeWordDetector implements WakeWordDetector {


    private static final double LIMITE_VOLUME = 50;

    @Override
    public boolean detect(byte[] audio) {


        double volume =
                calcularVolume(audio);


        System.out.println(
                "Volume: " + volume
        );


        if(volume > LIMITE_VOLUME){

            System.out.println(
                    "Possível fala detectada"
            );

            return true;

        }


        return false;

    }


    private double calcularVolume(byte[] audio){

        long soma = 0;


        for(byte b : audio){

            soma += Math.abs(b);

        }


        return soma / (double) audio.length;

    }

}