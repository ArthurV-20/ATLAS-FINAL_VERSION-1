package ui;

import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        // Este é um motor de arranque "limpo" que não herda do JavaFX.
        // Ele vai chamar a verdadeira aplicação em segurança.
        // ESSE é o VERDADEIRO MAIN
        AtlasApplication.main(args);
    }
}