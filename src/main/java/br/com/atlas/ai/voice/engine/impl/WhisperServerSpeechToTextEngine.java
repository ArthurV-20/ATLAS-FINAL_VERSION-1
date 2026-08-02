package br.com.atlas.ai.voice.engine.impl;

import br.com.atlas.ai.voice.engine.SpeechToTextEngine;

import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;

public class WhisperServerSpeechToTextEngine implements SpeechToTextEngine {

    private final String endpoint;
    private final HttpClient client;


    public WhisperServerSpeechToTextEngine(String endpoint) {

        this.endpoint = endpoint;
        this.client = HttpClient.newHttpClient();

    }


    @Override
    public String transcribe(File audio) throws Exception {
        System.out.println(
                "[AUDIO SIZE] "
                        + audio.length()
                        + " bytes"
        );

        long inicio =
                System.currentTimeMillis();


        String boundary =
                "----AtlasBoundary";


        byte[] body =
                createMultipartBody(
                        boundary,
                        audio
                );


        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(
                                URI.create(
                                        endpoint + "/inference"
                                )
                        )
                        .header(
                                "Content-Type",
                                "multipart/form-data; boundary=" + boundary
                        )
                        .POST(
                                HttpRequest.BodyPublishers.ofByteArray(body)
                        )
                        .build();


        HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );


        System.out.println(
                "[TEMPO] WHISPER SERVER: "
                        + (
                        System.currentTimeMillis()
                                - inicio
                )
                        + " ms"
        );

        System.out.println(
                "[AUDIO KB] "
                        + (audio.length() / 1024)
                        + " KB"
        );
        return extractText(
                response.body()
        );

    }


    private byte[] createMultipartBody(
            String boundary,
            File file
    ) throws Exception {


        String header =
                "--" + boundary + "\r\n"
                        + "Content-Disposition: form-data; name=\"file\"; filename=\""
                        + file.getName()
                        + "\"\r\n"
                        + "Content-Type: audio/wav\r\n\r\n";


        String footer =
                "\r\n--" + boundary + "--\r\n";


        byte[] fileBytes =
                Files.readAllBytes(
                        file.toPath()
                );


        byte[] headerBytes =
                header.getBytes();


        byte[] footerBytes =
                footer.getBytes();


        byte[] body =
                new byte[
                        headerBytes.length
                                + fileBytes.length
                                + footerBytes.length
                        ];


        System.arraycopy(
                headerBytes,
                0,
                body,
                0,
                headerBytes.length
        );


        System.arraycopy(
                fileBytes,
                0,
                body,
                headerBytes.length,
                fileBytes.length
        );


        System.arraycopy(
                footerBytes,
                0,
                body,
                headerBytes.length + fileBytes.length,
                footerBytes.length
        );


        return body;

    }


    private String extractText(String json) {


        int inicio =
                json.indexOf("\"text\":\"");


        if (inicio == -1) {
            return json;
        }


        inicio += 8;


        int fim =
                json.indexOf(
                        "\"",
                        inicio
                );


        if (fim == -1) {
            return json;
        }


        return json.substring(
                inicio,
                fim
        ).trim();

    }

}