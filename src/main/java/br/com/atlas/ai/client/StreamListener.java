package br.com.atlas.ai.client;

public interface StreamListener {

    void onToken(String token);

    void onComplete();

}