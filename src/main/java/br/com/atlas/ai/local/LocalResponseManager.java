package br.com.atlas.ai.local;

import br.com.atlas.ai.*;
import br.com.atlas.ai.memory.MemoryQueryDetector;
import br.com.atlas.ai.memory.MemoryQueryType;
import br.com.atlas.ai.router.IntentType;
import br.com.atlas.tool.ApplicationRegistry;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import br.com.atlas.calculation.CalculationEngine;

public class LocalResponseManager {


    private final MemoryQueryDetector detector =
            new MemoryQueryDetector();

    private final ApplicationRegistry registry =
            new ApplicationRegistry();
    private final CalculationEngine calculationEngine =
            new CalculationEngine();


    public AIResponse process(
            IntentType intent,
            AIRequest request
    ) {

        System.out.println(
                "[LOCAL RESPONSE] Intent: " + intent
        );

        System.out.println(
                "[LOCAL RESPONSE] Texto: " + request.getPrompt()
        );


        switch (intent) {


            case OPEN_APPLICATION:
                return handleOpenApplication(request);

            case TIME_QUERY:
                return handleTime();

            case DATE:
                return handleDate();

            case GREETING:
                return handleGreeting();

            case FAREWELL:
                return handleFarewell();
            case CALCULATION:
                return handleCalculation(request);

            case THANKS:
                return handleThanks();

            default:
                return handleDefault(request);
        }

    }
    private AIResponse handleDefault(
            AIRequest request
    ) {


        MemoryQueryType memoryType =
                detector.detect(
                        request.getPrompt()
                );


        if(memoryType ==
                MemoryQueryType.USER_WANTS_TO_SHARE){


            return new AIResponse(
                    "Claro, Senhor. Estou ouvindo."
            );

        }


        return null;

    }

    private AIResponse handleOpenApplication(
            AIRequest request
    ) {

        String texto =
                request.getPrompt()
                        .toLowerCase();


        String[] palavras =
                texto.split(" ");


        String aplicacao = null;


        for (String palavra : palavras) {

            if (registry.contains(palavra)) {

                aplicacao = registry.resolve(palavra);
                break;

            }

        }


        if(aplicacao == null){

            System.out.println(
                    "[LOCAL RESPONSE] Aplicação não encontrada."
            );

            return null;
        }


        System.out.println(
                "[LOCAL RESPONSE] Aplicação detectada: "
                        + aplicacao
        );


        AIResponse response =
                new AIResponse(
                        "Abrindo " + aplicacao + ", Senhor."
                );


        AIAction action =
                new AIAction(
                        AIActionType.OPEN_APPLICATION
                );


        action.addParameter(
                AIParameters.APPLICATION,
                aplicacao
        );


        response.addAction(action);


        return response;
    }
    private AIResponse handleTime(){

        int hora =
                java.time.LocalTime.now()
                        .getHour();

        int minuto =
                java.time.LocalTime.now()
                        .getMinute();


        String resposta =
                String.format(
                        "Agora são %02d:%02d, Senhor.",
                        hora,
                        minuto
                );


        return new AIResponse(resposta);

    }
    private AIResponse handleDate(){

        LocalDate hoje = LocalDate.now();

        String data =
                hoje.format(DateTimeFormatter.ofPattern(
                                "EEEE, 'dia' d 'de' MMMM 'de' yyyy",
                                new Locale("pt", "BR")
                        )


                );

        String resposta =
                "Hoje é " + data + ", Senhor.";

        return new AIResponse(resposta);

    }
    private AIResponse handleGreeting() {

        int hora =
                java.time.LocalTime.now().getHour();

        String saudacao;

        if (hora < 10) {
            saudacao =
                    "Bom dia, Senhor. Tão cedo, Senhor?";
        } else if (hora <12) {
            saudacao = "Hoje vai ser muito produtivo. Bom dia, Senhor.";
        } else if (hora < 18) {
            saudacao =
                    "Boa tarde, Senhor. Já tomou aquele cafézinho?";
        } else if (hora < 21) {
            saudacao =
                    "Boa noite, Senhor. Como você está?";
        } else {
            saudacao =
                    "Boa noite, Senhor. Você deveria descansar.";

        }

        return new AIResponse(saudacao);

    }


    private AIResponse handleFarewell() {

        return new AIResponse(
                "Até mais, Senhor."
        );

    }


    private AIResponse handleThanks() {

        return new AIResponse(
                "Sempre às ordens, Senhor."
        );

    }
    private AIResponse handleCalculation(AIRequest request) {

        String resultado =
                calculationEngine.calculate(
                        request.getPrompt()
                );

        return new AIResponse(resultado);

    }
}