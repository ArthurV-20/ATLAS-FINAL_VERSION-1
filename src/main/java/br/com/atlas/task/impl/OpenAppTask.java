package br.com.atlas.task.impl;

import br.com.atlas.task.AtlasTask;
import java.io.IOException;

/**
 * Classe responsável por executar a ação de abrir um aplicativo.
 * Ela "assina" o contrato AtlasTask usando a palavra-chave 'implements'.
 */
public class OpenAppTask implements AtlasTask {

    private final String applicationName;

    // Construtor: Pede o nome ou caminho do aplicativo que queremos abrir
    public OpenAppTask(String applicationName) {
        this.applicationName = applicationName;
    }

    @Override
    public void execute() throws Exception {

        ProcessBuilder processBuilder =
                new ProcessBuilder(applicationName);

        processBuilder.start();

    }

    @Override
    public String getSystemMessage() {
        return "Aplicativo " + applicationName + " iniciado com sucesso, Senhor.";
    }
}