package br.com.atlas.ai.voice.audio;

import javax.sound.sampled.*;
import java.io.*;

public class AudioRecorder {


    private static final AudioFormat FORMAT =
            new AudioFormat(
                    16000,
                    16,
                    1,
                    true,
                    false
            );


    // Sensibilidade do microfone
    private static final double SILENCE_THRESHOLD = 500;


    // Tempo de silêncio para encerrar
    private static final long SILENCE_DURATION = 500;


    // Segurança
    private static final long MAX_RECORD_TIME = 20000;


    // Guarda um pequeno pedaço antes da fala
    private static final long PRE_BUFFER_TIME = 300;



    public File record() throws Exception {


        System.out.println("[DEBUG] AudioRecorder otimizado");

        System.out.println("🎙 ATLAS ouvindo...");


        DataLine.Info info =
                new DataLine.Info(
                        TargetDataLine.class,
                        FORMAT
                );


        TargetDataLine microphone =
                (TargetDataLine)
                        AudioSystem.getLine(info);



        System.out.println(
                "Microfone usado: "
                        + microphone.getLineInfo()
        );



        microphone.open(FORMAT);

        microphone.start();



        ByteArrayOutputStream audio =
                new ByteArrayOutputStream();



        ByteArrayOutputStream preBuffer =
                new ByteArrayOutputStream();



        byte[] buffer =
                new byte[2048];



        boolean speaking = false;


        long start =
                System.currentTimeMillis();


        long lastSound =
                start;



        int preBufferLimit =
                (int)(
                        FORMAT.getFrameRate()
                                *
                                FORMAT.getFrameSize()
                                *
                                PRE_BUFFER_TIME
                                /
                                1000
                );



        while(true){


            int bytesRead =
                    microphone.read(
                            buffer,
                            0,
                            buffer.length
                    );



            double volume =
                    calculateRMS(
                            buffer,
                            bytesRead
                    );



            long now =
                    System.currentTimeMillis();



            /*
             * Antes da fala
             */
            if(!speaking){


                preBuffer.write(
                        buffer,
                        0,
                        bytesRead
                );



                // limita tamanho do buffer
                if(preBuffer.size() > preBufferLimit){

                    byte[] data =
                            preBuffer.toByteArray();


                    preBuffer.reset();


                    preBuffer.write(
                            data,
                            data.length - preBufferLimit,
                            preBufferLimit
                    );

                }



                if(volume > SILENCE_THRESHOLD){


                    speaking = true;


                    audio.write(
                            preBuffer.toByteArray()
                    );


                    preBuffer.reset();



                    audio.write(
                            buffer,
                            0,
                            bytesRead
                    );



                    lastSound = now;


                    System.out.println(
                            "Voz detectada."
                    );

                }


            }


            /*
             * Depois da fala
             */
            else{


                audio.write(
                        buffer,
                        0,
                        bytesRead
                );



                if(volume > SILENCE_THRESHOLD){

                    lastSound = now;

                }



                if(now - lastSound > SILENCE_DURATION){

                    break;

                }

            }



            if(now - start > MAX_RECORD_TIME){

                break;

            }

        }



        microphone.stop();

        microphone.close();



        File file =
                new File(
                        "audio.wav"
                );



        saveWav(
                audio.toByteArray(),
                file
        );



        System.out.println(
                "Áudio capturado."
        );


        return file;

    }




    private double calculateRMS(
            byte[] buffer,
            int length
    ){

        long sum = 0;


        for(int i = 0; i < length - 1; i += 2){


            int sample =
                    (buffer[i+1] << 8)
                            |
                            (buffer[i] & 0xff);



            sum += sample * sample;

        }



        double mean =
                sum / (length / 2.0);



        return Math.sqrt(mean);

    }




    private void saveWav(
            byte[] audio,
            File file
    ) throws Exception{


        ByteArrayInputStream bais =
                new ByteArrayInputStream(
                        audio
                );



        AudioInputStream stream =
                new AudioInputStream(
                        bais,
                        FORMAT,
                        audio.length / FORMAT.getFrameSize()
                );



        AudioSystem.write(
                stream,
                AudioFileFormat.Type.WAVE,
                file
        );

    }

}