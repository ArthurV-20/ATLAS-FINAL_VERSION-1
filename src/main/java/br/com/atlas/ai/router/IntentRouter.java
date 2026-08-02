package br.com.atlas.ai.router;

public class IntentRouter {


    public IntentType analyze(String text) {
        System.out.println("[TEXTO RAW] >>>" + text + "<<<");
        String command = text.toLowerCase().trim();
        command = command.replace("*", "x");
        System.out.println("[TEXTO] " + command);
        // =============================
// NEXUS PROTOCOL
// =============================

        if(command.contains("ativar modo nexus")
                || command.contains("ativar nexus")
                || command.contains("nexus protocol")
                || command.contains("modo nexus")
                || command.contains("modo pro")
                || command.contains("ativar modo pro")){


            System.out.println("NEXUS_ENABLE");


            return IntentType.NEXUS_ENABLE;

        }



        if(command.contains("desativar nexus")
                || command.contains("sair do nexus")
                || command.contains("voltar ao modo padrão")
                || command.contains("voltar para o padrão")
                || command.contains("modo padrão")){


            System.out.println("NEXUS_DISABLE");


            return IntentType.NEXUS_DISABLE;

        }
        //AQUI é a parte do SPOTIFY
        if(command.contains("spotify")
                || command.contains("ouvir musica")
                || command.contains("ouvir música")
                || command.contains("ouvi música")
                || command.contains("ouvi musica")
                || command.contains("escutar musica")
                || command.contains("tocar musica")){

            return IntentType.OPEN_APPLICATION;

        }

        // Aqui preferi botar DESPEDIDAS
        if(command.contains("até amanhã")
                || command.contains("ate amanhã")
                || command.contains("até mais")
                || command.contains("até logo")
                || command.contains("tchau")
                || command.contains("valeu")
                || command.equals("ok")){

            System.out.println("FAREWELL");

            return IntentType.FAREWELL;
        }
        //Aqui é o ABRIR APLICATIVOS
        if (command.contains("abra")
                || command.contains("abrir")
                || command.contains("inicie")
                || command.contains("executa")
                || command.contains("rodar")
                || command.contains("abre")
                || command.contains("a bre")
                || command.contains("execut")
                || command.contains("abr")
                || command.contains("abrei")
                || command.contains("abro")
                || command.contains("abrou")
                || command.contains("abroi")
                || command.contains("abriei")) {

            System.out.println("OPEN_APPLICATION");

            return IntentType.OPEN_APPLICATION;
        }

        System.out.println(
                "[DEBUG GREETING CHECK] " + command.contains("ola")
        );
        // SAUDAÇÕES(é claro)
        if (command.equals("olá")
                || command.equals("ola")
                || command.equals("oi")
                || command.equals("oie")
                || command.contains("bom dia")
                || command.contains("boa tarde")
                || command.contains("boa noite")
                || command.contains("ula")
                || command.contains("ola!")
                || command.contains("ula!")
                || command.contains("ola?")) {

            System.out.println("GREETING");

            return IntentType.GREETING;

        }


        System.out.println("QUESTION");
        if(command.contains("obrigado")
                || command.contains("obrigada")
                || command.contains("valeu")
                || command.contains("agradeço")
                || command.contains("agradeco")){

            System.out.println("THANKS");

            return IntentType.THANKS;

        }

        if(command.contains("que horas")
                || command.contains("qual horario")
                || command.contains("qual horário")
                || command.contains("horas são")
                || command.contains("horas é")
                || command.contains("que hora é")
                || command.contains("que horas são")) {

            return IntentType.TIME_QUERY;

        }
        if (command.contains("que dia")
                || command.contains("data")
                || command.contains("dia de hoje")
                || command.contains("hoje é")
                || command.contains("que dia é hoje")) {

            System.out.println("DATE");
            return IntentType.DATE;
        }
        if(command.matches(".*\\d+\\s*[+\\-*/]\\s*\\d+.*")
                || command.contains("-")
                || command.contains("/")
                || command.contains("mais")
                || command.contains("menos")
                || command.contains("vezes")
                || command.contains("dividido")){

            System.out.println("CALCULATION");
            return IntentType.CALCULATION;

        }
        return IntentType.QUESTION;
    }

}