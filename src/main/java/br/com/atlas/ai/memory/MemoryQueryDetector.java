package br.com.atlas.ai.memory;

import java.text.Normalizer;
import java.util.List;

public class MemoryQueryDetector {


    private static final List<String> NAME_PATTERNS =
            List.of(
                    "qual meu nome",
                    "qual é meu nome",
                    "qual o meu nome",
                    "qual é o meu nome",
                    "como me chamo",
                    "qual nome eu te falei",
                    "qual foi o nome que eu te falei",
                    "você sabe meu nome",
                    "você sabe qual meu nome",
                    "sabe meu nome",
                    "lembra meu nome",
                    "lembra do meu nome",
                    "diz meu nome",
                    "me diga meu nome",
                    "fala meu nome"
            );


    private static final List<String> COLOR_PATTERNS =
            List.of(
                    "minha cor",
                    "qual minha cor",
                    "qual é minha cor",
                    "qual a minha cor",
                    "minha cor favorita",
                    "qual minha cor favorita",
                    "qual é minha cor favorita",
                    "qual a minha cor favorita",
                    "cor que eu gosto",
                    "qual cor eu gosto",
                    "você lembra minha cor",
                    "sabe minha cor"
            );


    private static final List<String> PROJECT_PATTERNS =
            List.of(
                    "meu projeto",
                    "meus projetos",
                    "qual meu projeto",
                    "quais meus projetos",
                    "quais projetos eu tenho",
                    "quais são meus projetos",
                    "lembra dos meus projetos",
                    "você lembra dos meus projetos",
                    "sabe meus projetos"
            );


    private static final List<String> LANGUAGE_PATTERNS =
            List.of(
                    "minha linguagem",
                    "qual minha linguagem",
                    "qual linguagem eu programo",
                    "qual linguagem gosto",
                    "qual linguagem eu gosto",
                    "minha linguagem favorita",
                    "linguagem favorita"
            );



    public MemoryQueryType detect(
            String message
    ){


        if(message == null || message.isBlank()){

            return MemoryQueryType.NONE;

        }



        String text =
                normalize(message);



        System.out.println(
                "[MEMORY DETECTOR] Texto: "
                        + text
        );



        if(matches(text, NAME_PATTERNS)){

            System.out.println(
                    "[MEMORY DETECTOR] NAME"
            );

            return MemoryQueryType.NAME;

        }



        if(matches(text, COLOR_PATTERNS)){

            System.out.println(
                    "[MEMORY DETECTOR] FAVORITE_COLOR"
            );

            return MemoryQueryType.FAVORITE_COLOR;

        }



        if(matches(text, PROJECT_PATTERNS)){

            System.out.println(
                    "[MEMORY DETECTOR] PROJECT"
            );

            return MemoryQueryType.PROJECT;

        }





        // =========================
// NOME
// =========================

        if(matches(text, NAME_PATTERNS)){

            System.out.println(
                    "[MEMORY DETECTOR] NAME"
            );

            return MemoryQueryType.NAME;

        }


// =========================
// COR FAVORITA
// =========================

        if(matches(text, COLOR_PATTERNS)){

            System.out.println(
                    "[MEMORY DETECTOR] FAVORITE_COLOR"
            );

            return MemoryQueryType.FAVORITE_COLOR;

        }


// =========================
// PROJETO
// =========================

        if(matches(text, PROJECT_PATTERNS)){

            System.out.println(
                    "[MEMORY DETECTOR] PROJECT"
            );

            return MemoryQueryType.PROJECT;

        }


// =========================
// LINGUAGEM
// =========================

        if(matches(text, LANGUAGE_PATTERNS)){

            System.out.println(
                    "[MEMORY DETECTOR] PROGRAMMING_LANGUAGE"
            );

            return MemoryQueryType.PROGRAMMING_LANGUAGE;

        }


// =========================
// USUÁRIO QUER COMPARTILHAR
// =========================

        if(matches(text, SHARE_PATTERNS)){

            System.out.println(
                    "[MEMORY DETECTOR] USER_WANTS_TO_SHARE"
            );

            return MemoryQueryType.USER_WANTS_TO_SHARE;

        }


// =========================
// MEMÓRIA GERAL
// =========================

        if(text.contains("voce lembra")
                || text.contains("como falei")
                || text.contains("anteriormente")
                || text.contains("eu te falei")
                || text.contains("te falei antes")){

            System.out.println(
                    "[MEMORY DETECTOR] GENERAL_MEMORY"
            );

            return MemoryQueryType.GENERAL_MEMORY;

        }

        return MemoryQueryType.NONE;

    }

    private String normalize(String message){

        message = Normalizer.normalize(
                message,
                Normalizer.Form.NFD
        );

        message = message.replaceAll("\\p{M}", "");

        return message
                .toLowerCase()
                .replaceAll("[^a-z0-9 ]", " ")
                .replaceAll("\\s+", " ")
                .trim();

    }

    private boolean matches(
            String text,
            List<String> patterns
    ){

        return patterns.stream()
                .anyMatch(text::contains);

    }
    private static final List<String> SHARE_PATTERNS =
            List.of(
                    "voce quer saber uma coisa sobre mim",
                    "quer saber algo sobre mim",
                    "vou te contar uma coisa",
                    "quero te contar algo",
                    "tenho uma coisa para falar",
                    "preciso te contar algo"
            );
}