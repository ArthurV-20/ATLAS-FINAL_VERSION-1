package br.com.atlas.ai.streaming;

public class StreamResponseHandler {

    private final StringBuilder resposta =
            new StringBuilder();

    private long inicio;
    private long primeiroToken;


    public void start(){

        inicio = System.currentTimeMillis();

        primeiroToken = 0;

        resposta.setLength(0);

    }


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

    }


    public void onComplete(){

        long total =
                System.currentTimeMillis() - inicio;


        System.out.println(
                "\n[STREAM FINALIZADO]"
        );


        System.out.println(
                "[TEMPO TOTAL STREAM] "
                        + total
                        + " ms"
        );

    }


    public String getResponse(){

        return resposta.toString();

    }
//RESPONDER
}