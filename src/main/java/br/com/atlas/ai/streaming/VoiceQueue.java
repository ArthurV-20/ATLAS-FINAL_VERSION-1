package br.com.atlas.ai.streaming;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class VoiceQueue {

    private final BlockingQueue<String> fila =
            new LinkedBlockingQueue<>();


    public void adicionar(String texto){

        fila.add(texto);

    }


    public String pegar()
            throws InterruptedException {

        return fila.take();

    }

}