package br.com.atlas.ai.router;

public class AIModelRouter {

    public AIModelType chooseModel(
            IntentType intent,
            String message
    ) {

        switch (intent) {

            case GREETING:
            case SEARCH:
                return AIModelType.FAST;

            case QUESTION:

                String text = normalize(message);

                // Perguntas que exigem raciocínio sempre usam o 7B
                if (needsReasoning(text)) {
                    return AIModelType.REASONING;
                }

                // Perguntas simples usam o 1.5B
                if (isSimpleQuestion(text)) {
                    return AIModelType.FAST;
                }

                // Dúvida: usa o modelo mais inteligente
                return AIModelType.REASONING;

            case UNKNOWN:
                return AIModelType.REASONING;

            default:
                return AIModelType.FAST;
        }
    }

    private String normalize(String message) {

        String text = message.toLowerCase();

        int index = text.lastIndexOf("usuário:");

        if (index != -1) {
            text = text.substring(index + "usuário:".length()).trim();
        }

        return text
                .replace("?", "")
                .replace(".", "")
                .replace(",", "")
                .trim();
    }

    private boolean isSimpleQuestion(String text) {

        // Perguntas curtas geralmente são simples
        if (text.length() <= 60) {
            return true;
        }

        return text.startsWith("o que é")
                || text.startsWith("quem é")
                || text.startsWith("qual é")
                || text.startsWith("qual a")
                || text.startsWith("qual o")
                || text.startsWith("onde")
                || text.startsWith("quando")
                || text.startsWith("quanto")
                || text.startsWith("quanto é")
                || text.startsWith("quantos")
                || text.startsWith("quais");
    }

    private boolean needsReasoning(String text) {

        return text.contains("compar")
                || text.contains("diferen")
                || text.contains("explic")
                || text.contains("analis")
                || text.contains("cri")
                || text.contains("escrev")
                || text.contains("desenvolv")
                || text.contains("implement")
                || text.contains("código")
                || text.contains("algoritmo")
                || text.contains("projeto")
                || text.contains("estratég")
                || text.contains("passo a passo")
                || text.contains("plano")
                || text.contains("arquitetura")
                || text.contains("otimiz")
                || text.contains("refator");
    }
}