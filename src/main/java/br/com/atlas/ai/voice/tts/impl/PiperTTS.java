package br.com.atlas.ai.voice.tts.impl;


import br.com.atlas.ai.voice.tts.TextToSpeechEngine;


import java.io.BufferedReader;
import java.io.InputStreamReader;


public class PiperTTS implements TextToSpeechEngine {


    private final String piperPath;
    private final String modelPath;



    public PiperTTS(
            String piperPath,
            String modelPath
    ){

        this.piperPath = piperPath;
        this.modelPath = modelPath;

    }



    @Override
    public void speak(
            String text
    ) throws Exception {


        ProcessBuilder builder =
                new ProcessBuilder(
                        piperPath,
                        "--model",
                        modelPath,
                        "--output_file",
                        "atlas_voice.wav"
                );


        Process process =
                builder.start();



        process.getOutputStream()
                .write(
                        text.getBytes()
                );


        process.getOutputStream()
                .close();



        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(
                                process.getInputStream()
                        )
                );
        System.out.println("[PIPER OUTPUT] Finalizado");

        while(reader.readLine() != null){

        }
        int exitCode = process.waitFor();

        System.out.println("[PIPER EXIT CODE] " + exitCode);
        //process.waitFor();


    }

}