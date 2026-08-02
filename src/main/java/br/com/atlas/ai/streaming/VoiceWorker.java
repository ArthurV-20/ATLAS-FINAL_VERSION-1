package br.com.atlas.ai.streaming;

import br.com.atlas.ai.voice.VoiceManager;


public class VoiceWorker extends Thread {


    private final VoiceQueue queue;
    private final VoiceManager voiceManager;


    public VoiceWorker(
            VoiceQueue queue,
            VoiceManager voiceManager
    ){

        this.queue = queue;
        this.voiceManager = voiceManager;

        setName("ATLAS-VOICE-WORKER");

    }



    @Override
    public void run(){

        while(true){

            try {

                String frase =
                        queue.pegar();


                System.out.println(
                        "[VOICE WORKER] Falando: "
                                + frase
                );


                voiceManager.speak(frase);


            } catch(Exception e){

                e.printStackTrace();

            }

        }

    }

}