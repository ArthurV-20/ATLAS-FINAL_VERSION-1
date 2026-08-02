package br.com.atlas.ai.services;
import java.time.DayOfWeek;
import java.time.LocalDate;
//"/audio/intro/Back_In_Black.wav"
public class IntroMusicCatalog {

    public String getTodayMusic() {

        DayOfWeek day =
                LocalDate.now().getDayOfWeek();
//É meio óbvio mas aqui é o catálogo de músicas
        return switch (day) {

            case MONDAY -> "/audio/intro/What_a_Wonderful_World.wav";

            case TUESDAY -> "/audio/intro/We_Own_It.wav";

            case WEDNESDAY -> "/audio/intro/Oitavo_Anjo.wav";

            case THURSDAY -> "/audio/intro/The_Winner_Takes_It_All.wav";

            case FRIDAY -> "/audio/intro/Should_I_Stay.wav";

            case SATURDAY -> "/audio/intro/Back_In_Black.wav";

            case SUNDAY -> "/audio/intro/A_Man_Without_Love.wav";
        };
    }
}
