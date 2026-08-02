package br.com.atlas.ai.memory.longterm.rules;

import br.com.atlas.ai.memory.longterm.*;

public class ProgrammingLanguageMemoryRule
        implements MemoryRule {

    @Override
    public boolean matchesSave(String message) {

        return message.toLowerCase()
                .contains("gosto de programar em");

    }

    @Override
    public void save(
            String message,
            MemoryManager memoryManager
    ) {

        String lower =
                message.toLowerCase();

        String linguagem =
                message.substring(
                        lower.indexOf("gosto de programar em")
                                + "gosto de programar em".length()
                            );
                                linguagem = linguagem
                                .replace("\\n", "")
                                .replace("\n", "")
                                .replace(".", "")
                                .trim();

        if(linguagem.isBlank()){
            return;
        }

        memoryManager.remember(
                "usuario.linguagem_programacao",
                linguagem,
                MemoryCategory.PREFERENCE,
                7
        );

    }

    @Override
    public boolean matchesRecall(String message) {

        String lower =
                message.toLowerCase();

        return lower.contains("linguagem")
                || lower.contains("program");

    }

    @Override
    public MemoryEntry recall(
            LongTermMemory memory
    ) {

        return memory.getEntry(
                "usuario.linguagem_programacao"
        );

    }

}