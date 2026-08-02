package br.com.atlas.ai.streaming;

import br.com.atlas.ai.client.StreamListener;
import br.com.atlas.ai.voice.VoiceManager;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class VoiceStreamManager implements StreamListener {


    private final VoiceManager voiceManager;


    private final BlockingQueue<String> fila =
            new LinkedBlockingQueue<>();


    private final StringBuilder frase =
            new StringBuilder();



    public VoiceStreamManager(
            VoiceManager voiceManager
    ){

        this.voiceManager = voiceManager;

        iniciarWorker();

    }



    @Override
    public void onToken(String token){

        frase.append(token);


        String atual =
                frase.toString();


        boolean temPontuacao =
                token.contains(".")
                        ||
                        token.contains("?")
                        ||
                        token.contains("!");


        boolean muitoGrande =
                atual.length() >= 120;


        if(temPontuacao || muitoGrande){

            adicionarFrase();

        }

    }



    private void adicionarFrase(){

        String texto =
                frase.toString()
                        .trim();


        frase.setLength(0);


        if(texto.isEmpty()){
            return;
        }


        System.out.println(
                "[VOICE QUEUE] Adicionando: "
                        + texto
        );


        fila.offer(texto);

    }



    private void iniciarWorker(){


        Thread worker =
                new Thread(() -> {


                    while(true){

                        try {

                            String texto =
                                    fila.take();


                            System.out.println(
                                    "[VOICE WORKER] Falando: "
                                            + texto
                            );


                            voiceManager.speak(texto);


                        } catch(Exception e){

                            e.printStackTrace();

                        }

                    }


                });


        worker.setDaemon(true);
        worker.start();

    }



    @Override
    public void onComplete(){


        if(frase.length() > 0){

            adicionarFrase();

        }


        System.out.println(
                "[VOICE STREAM FINALIZADO]"
        );

    }

}