package br.com.atlas.ai.services;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URL;

public class IntroMusicService {

    private MediaPlayer mediaPlayer;

    public void play(String musicPath){

        URL music =
                getClass()
                        .getResource(musicPath);

        if (music == null) {
            System.out.println("Música da ATLAS não encontrada.");
            return;
        }

        System.out.println("Arquivo encontrado:");
        System.out.println(music);

        Media media = new Media(music.toExternalForm());

        media.setOnError(() ->
                System.out.println("Erro Media: " + media.getError())
        );

        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnReady(() -> {
            System.out.println("Player pronto.");
            mediaPlayer.play();
        });

        mediaPlayer.setOnError(() ->
                System.out.println("Erro Player: " + mediaPlayer.getError())
        );
    }
}