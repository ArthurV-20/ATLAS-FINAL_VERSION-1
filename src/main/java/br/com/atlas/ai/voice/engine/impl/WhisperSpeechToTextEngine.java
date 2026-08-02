package br.com.atlas.ai.voice.engine.impl;

import br.com.atlas.ai.voice.engine.SpeechToTextEngine;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class WhisperSpeechToTextEngine implements SpeechToTextEngine {


    private final String whisperPath;
    private final String modelPath;


    public WhisperSpeechToTextEngine(
            String whisperPath,
            String modelPath
    ){

        this.whisperPath = whisperPath;
        this.modelPath = modelPath;

    }



    @Override
    public String transcribe(File audio)
            throws Exception {


        ProcessBuilder builder =
                new ProcessBuilder(
                        whisperPath,
                        "-m",
                        modelPath,
                        "-l",
                        "pt",
                        "-f",
                        audio.getAbsolutePath()
                );


        builder.redirectErrorStream(true);


        Process process =
                builder.start();



        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(
                                process.getInputStream()
                        )
                );


        StringBuilder output =
                new StringBuilder();


        String line;


        String transcricao = "";

        while ((line = reader.readLine()) != null) {

            line = line.trim();

            // Procuramos apenas as linhas de transcrição
            if (line.startsWith("[")) {

                int indice = line.indexOf("]");

                if (indice != -1) {

                    String texto =
                            line.substring(indice + 1)
                                    .trim();

                    if (!texto.isBlank()) {

                        transcricao += texto + " ";

                    }

                }

            }

        }


        process.waitFor();

       // return transcricao.trim();
        String resultado =
                output.toString();


        resultado = resultado
                .replaceAll("\\[.*?\\]", "")
                .replaceAll("whisper_.*", "")
                .trim();

        return transcricao.trim();
        //return resultado;

    }
}