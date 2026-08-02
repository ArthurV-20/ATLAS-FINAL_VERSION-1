package br.com.atlas.ai.voice.audio;

import javax.sound.sampled.*;

public class ListMicrophones {

    public static void main(String[] args) {

        Mixer.Info[] mixers =
                AudioSystem.getMixerInfo();


        for (Mixer.Info mixer : mixers) {

            Mixer m =
                    AudioSystem.getMixer(mixer);


            Line.Info[] lines =
                    m.getTargetLineInfo();


            if(lines.length > 0){

                System.out.println(
                        "MICROFONE: "
                                + mixer.getName()
                );

            }

        }

    }

}