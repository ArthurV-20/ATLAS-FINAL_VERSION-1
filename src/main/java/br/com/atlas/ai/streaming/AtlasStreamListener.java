package br.com.atlas.ai.streaming;

import br.com.atlas.ai.client.StreamListener;

public class AtlasStreamListener implements StreamListener {


    private final StringBuilder resposta =
            new StringBuilder();


    private final StreamListener delegate;


    private long inicio;
    private long primeiroToken;


    public AtlasStreamListener() {

        this.delegate = null;

    }


    public AtlasStreamListener(StreamListener delegate) {

        this.delegate = delegate;

    }


    @Override
    public void onToken(String token){


        if(primeiroToken == 0){

            primeiroToken =
                    System.currentTimeMillis() - inicio;

            System.out.println(
                    "\n[PRIMEIRO TOKEN] "
                            + primeiroToken
                            + " ms"
            );

        }


        System.out.print(token);

        resposta.append(token);


        // envia para quem estiver ouvindo
        if(delegate != null){

            delegate.onToken(token);

        }

    }


    @Override
    public void onComplete(){

        System.out.println(
                "\n[STREAM FINALIZADO]"
        );


        if(delegate != null){

            delegate.onComplete();

        }

    }


    public String getResponse(){

        return resposta.toString();

    }


    public void start(){

        inicio = System.currentTimeMillis();
        primeiroToken = 0;

    }

}