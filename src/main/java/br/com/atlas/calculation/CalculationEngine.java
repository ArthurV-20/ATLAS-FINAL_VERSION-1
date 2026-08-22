package br.com.atlas.calculation;

public class CalculationEngine {

    public String calculate(String text) {

        String expression = normalize(text);

        expression = expression
                .replace("\\n", "")
                .replace("\n", "")
                .replace("?", "")
                .replace("!", "")
                .trim();

        System.out.println("[EXPRESSÃO] " + expression);

        expression = expression
                .replace("+", " + ")
                .replace("-", " - ")
                .replace("*", " * ")
                .replace("/", " / ");

        expression = expression
                .replaceAll("\\s+", " ")
                .trim();

        String[] tokens = expression.split("\\s+");

        System.out.println("[TOKENS]");
        for (String token : tokens) {
            System.out.println("[" + token + "]");
        }

        if (tokens.length != 3) {
            return "Não consegui interpretar essa conta, Senhor.";
        }

        try {

            double a = Double.parseDouble(tokens[0]);
            String operator = tokens[1];
            double b = Double.parseDouble(tokens[2]);

            double result;

            switch (operator) {

                case "+" -> result = a + b;

                case "-" -> result = a - b;

                case "*" -> result = a * b;

                case "/" -> {

                    if (b == 0) {
                        return "Não é possível dividir por zero, Senhor.";
                    }

                    result = a / b;
                }

                default -> {
                    return "Operação não suportada.";
                }
            }

            if (result == (long) result) {
                return "O resultado é " + (long) result + ".";
            }

            return "O resultado é " + result + ".";

        } catch (NumberFormatException e) {

            return "Não consegui interpretar essa conta, Senhor.";

        }
    }

    private String normalize(String text) {

        text = text.toLowerCase();

        text = text.replace("quanto é", "");
        text = text.replace("quanto e", "");
        text = text.replace("tanto e", "");
        text = text.replace("tanto é", "");
        text = text.replace("calcule", "");
        text = text.replace("calcula", "");
        text = text.replace("me diga", "");
        text = text.replace("qual é", "");
        text = text.replace("qual e", "");

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

        text = text.replace(" x ", " * ");

        text = text.replace(",", ".");

        text = text
                .replace("\n", "")
                .replace("\r", "")
                .replaceAll("\\s+", " ")
                .trim();

        return text;
    }
    //eu vou mexer nisso. Falar com o chat
}