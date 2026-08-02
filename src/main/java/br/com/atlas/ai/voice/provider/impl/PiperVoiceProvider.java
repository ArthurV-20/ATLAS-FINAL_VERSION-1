package br.com.atlas.ai.voice.provider.impl;

import br.com.atlas.ai.voice.provider.VoiceProvider;

import java.io.File;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class PiperVoiceProvider implements VoiceProvider {

    private final String piperPath;
    private final String modelPath;

    // Streaming (reservado para futura implementação)
    private Process piperProcess;
    private OutputStream piperInput;

    private static boolean speaking = false;


    public PiperVoiceProvider(
            String piperPath,
            String modelPath
    ) {

        this.piperPath = piperPath;
        this.modelPath = modelPath;

    }


    @Override
    public synchronized void speak(String text)
            throws Exception {


        if (speaking) {

            System.out.println(
                    "[PIPER] Já está falando. Ignorando nova chamada."
            );

            return;

        }


        speaking = true;


        try {

            System.out.println(
                    "[TEXTO RECEBIDO PELO PIPER] "
                            + text
            );


            long inicioTotal =
                    System.currentTimeMillis();



            File wavFile =
                    new File(
                            "atlas_voice_"
                                    + System.currentTimeMillis()
                                    + ".wav"
                    );


            String wavPath =
                    wavFile.getAbsolutePath();



            // =========================
            // INICIALIZAÇÃO PIPER
            // =========================

            long inicioPiper =
                    System.currentTimeMillis();


            ProcessBuilder builder =
                    new ProcessBuilder(
                            piperPath,
                            "-m",
                            modelPath,
                            "--length_scale",
                            "1.2",
                            "-f",
                            wavPath
                    );


            builder.redirectErrorStream(true);


            Process process =
                    builder.start();


            System.out.println(
                    "[PIPER START: "
                            +
                            (System.currentTimeMillis()
                                    - inicioPiper)
                            +
                            " ms]"
            );



            // =========================
            // ENVIO TEXTO
            // =========================

            try(OutputStream stdin =
                        process.getOutputStream()) {


                stdin.write(
                        text.getBytes(
                                StandardCharsets.UTF_8
                        )
                );


                stdin.flush();

            }



            process.getInputStream()
                    .transferTo(
                            OutputStream.nullOutputStream()
                    );



            int exitCode =
                    process.waitFor();


            System.out.println(
                    "[PIPER EXIT] "
                            + exitCode
            );



            System.out.println(
                    "[PIPER] Síntese: "
                            +
                            (System.currentTimeMillis()
                                    - inicioPiper)
                            +
                            " ms"
            );



            System.out.println(
                    "[WAV TAMANHO] "
                            +
                            wavFile.length()
                            +
                            " bytes"
            );



            // =========================
            // WARMUP AUDIO
            // =========================

            warmupAudio();

            // =========================
            // REPRODUÇÃO
            // =========================

            long inicioPlay =
                    System.currentTimeMillis();


            System.out.println(
                    "[APLAY START]"
            );


            long inicioPlay1 =
                    System.currentTimeMillis();

            Process play =
                    new ProcessBuilder(
                            "aplay",
                            "-q",
                            wavPath
                    ).start();


            System.out.println(
                    "[APLAY START]"
            );


            new Thread(() -> {

                try {

                    int exit =
                            play.waitFor();

                    System.out.println(
                            "[APLAY EXIT] " + exit
                    );
                    if (wavFile.exists()) {

                        boolean apagado = wavFile.delete();

                        System.out.println(
                                "[WAV REMOVIDO] " + apagado
                        );

                    }
                    System.out.println(
                            "[APLAY TEMPO FINAL] "
                                    +
                                    (System.currentTimeMillis() - inicioPlay)
                                    +
                                    " ms"
                    );

                } catch (Exception e) {

                    e.printStackTrace();

                }

            }).start();

            System.out.println(
                    "[APLAY TEMPO] "
                            +
                            (System.currentTimeMillis() - inicioPlay)
                            +
                            " ms"
            );



            System.out.println(
                    "[PIPER TOTAL]"
                            +
                            (System.currentTimeMillis()
                                    - inicioTotal)
                            +
                            " ms"
            );


        } finally {

            speaking = false;

        }

    }



    private void warmupAudio()
            throws Exception {


        Process warmup =
                new ProcessBuilder(
                        "aplay",
                        "-q",
                        "/home/srgamestv/Documentos/JAVA PROJECTS/ATLAS/silence.wav"
                ).start();



        warmup.waitFor();


        System.out.println(
                "[AUDIO WARMUP OK]"
        );

    }



    // =========================
    // STREAMING FUTURO
    // =========================


    @Override
    public synchronized void startStream()
            throws Exception {


        System.out.println(
                "[PIPER] Iniciando stream de voz"
        );


        ProcessBuilder builder =
                new ProcessBuilder(
                        piperPath,
                        "-m",
                        modelPath
                );


        builder.redirectErrorStream(true);


        piperProcess =
                builder.start();


        piperInput =
                piperProcess.getOutputStream();

    }



    @Override
    public synchronized void speakChunk(
            String chunk
    )
            throws Exception {


        if(piperInput == null){

            throw new IllegalStateException(
                    "Stream do Piper não iniciado"
            );

        }


        piperInput.write(
                chunk.getBytes(
                        StandardCharsets.UTF_8
                )
        );


        piperInput.flush();


        System.out.println(
                "[PIPER CHUNK] "
                        + chunk
        );

    }



    @Override
    public synchronized void endStream()
            throws Exception {


        if(piperInput != null){

            piperInput.close();

        }


        if(piperProcess != null){

            int exit =
                    piperProcess.waitFor();


            System.out.println(
                    "[PIPER STREAM EXIT] "
                            + exit
            );

        }


        piperInput = null;
        piperProcess = null;

    }

}