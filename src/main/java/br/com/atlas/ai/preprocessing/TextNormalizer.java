
package br.com.atlas.ai.preprocessing;

public class TextNormalizer {

    public String normalize(String text) {

        if (text == null) {
            return "";
        }

        text = text.toLowerCase();

        // Quebras de linha
        text = text.replace("\\n", "");
        text = text.replace("\n", "");

        // Correções de transcrição
        text = text.replace("na vegador", "navegador");
        text = text.replace("navega dor", "navegador");

        text = text.replace("ne*us", "nexus");
        text = text.replace("maxus", "nexus");
        text = text.replace("mecsus", "nexus");
        text = text.replace("mexos", "nexus");
        text = text.replace("nextsush", "nexus");

        text = text.replace("v s code", "vs code");
        text = text.replace("intelij", "intellij");

        // Comandos/conversação
        text = text.replace("me diga", "");
        text = text.replace("qual é", "");
        text = text.replace("qual e", "");

        // Operações matemáticas
        text = text.replace("somado a", "+");
        text = text.replace("mais", "+");

        text = text.replace("subtraído de", "-");
        text = text.replace("subtraido de", "-");
        text = text.replace("menos", "-");

        text = text.replace("multiplicado por", "*");
        text = text.replace("multiplicado", "*");
        text = text.replace("vezes", "*");


        text = text.replace("dividido por", "/");
        text = text.replace("dividido", "/");
        text = text.replace("dividir", "/");
        text = text.replace("dividir por", "/");
        text = text.replace("/", "/");

        text = text.replace(" x ", "*");

        text = text.replace("=", "");

        // Pontuação
        text = text.replace("?", "");
        text = text.replace("!", "");
        text = text.replace(",", ".");

        // Espaços
        text = text.replaceAll("\\s+", " ");
        text = text.trim();

        // Isso remove pontuação apenas no final
        // Tipo: "abrir navegador." -> "abrir navegador"
        text = text.replaceAll("[!?.,;:]+$", "");

        text = text.trim();

        return text;
    }
}
