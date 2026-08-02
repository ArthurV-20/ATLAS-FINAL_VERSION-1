package br.com.atlas.ai.voice.wakeword;


public interface WakeWordDetector {

    boolean detect(byte[] audio);

}