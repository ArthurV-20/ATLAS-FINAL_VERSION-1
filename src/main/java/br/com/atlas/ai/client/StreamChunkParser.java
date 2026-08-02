package br.com.atlas.ai.client;
public class StreamChunkParser {


    public static String extractText(String json){


        if(json.contains("\"done\":true")){
            return "";
        }


        int inicio =
                json.indexOf("\"response\":\"");


        if(inicio == -1){
            return "";
        }


        inicio += "\"response\":\"".length();


        int fim =
                json.indexOf("\",\"done\"", inicio);


        if(fim == -1){
            return "";
        }


        return json.substring(inicio, fim)
                .replace("\\n", "\n")
                .replace("\\\"", "\"")
                .replace("\\\\", "\\");

    }

}