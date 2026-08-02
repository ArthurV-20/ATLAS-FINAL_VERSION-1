package br.com.atlas.ai.voice.audio;

import javax.sound.sampled.*;

public class AudioStream {


    private static final AudioFormat FORMAT =
            new AudioFormat(
                    16000,
                    16,
                    1,
                    true,
                    false
            );


    private TargetDataLine microphone;



    public void start() throws Exception {


        DataLine.Info info =
                new DataLine.Info(
                        TargetDataLine.class,
                        FORMAT
                );


        microphone =
                (TargetDataLine)
                        AudioSystem.getLine(info);



        microphone.open(FORMAT);

        microphone.start();


        System.out.println(
                "Microfone contínuo iniciado."
        );

    }



    public byte[] readChunk(){


        byte[] buffer =
                new byte[2048];


        microphone.read(
                buffer,
                0,
                buffer.length
        );


        return buffer;

    }



    public void stop(){

        microphone.stop();
        microphone.close();

    }

}