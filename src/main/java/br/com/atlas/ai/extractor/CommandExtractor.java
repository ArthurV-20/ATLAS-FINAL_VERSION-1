package br.com.atlas.ai.extractor;

public class CommandExtractor {

    public String extractApplication(String text) {

        String command = text.toLowerCase();

        command = command
                .replace("abrir", "")
                .replace("abra", "")
                .replace("iniciar", "")
                .replace("inicie", "")
                .replace("executar", "")
                .replace("executa", "")
                .replace("rodar", "")
                .trim();
        if(text.contains("naveg")
                || text.contains("vega")
                || text.contains("veg")
                || text.contains("nav ega")
                || text.contains("navega")
                || text.contains("vega do")){

            return "navegador";
        }
        return command;
    }

}