package br.com.atlas.ai.memory.longterm.rules;

import br.com.atlas.ai.memory.longterm.*;

public class ProjectMemoryRule
        implements MemoryRule {

    @Override
    public boolean matchesSave(String message) {

        String lower =
                message.toLowerCase();

        return lower.contains("meu projeto")
                && lower.contains("chamado");

    }

    @Override
    public void save(
            String message,
            MemoryManager memoryManager
    ) {

        String lower =
                message.toLowerCase();

        String projeto =
                message.substring(
                        lower.indexOf("chamado")
                                + "chamado".length()
                );

                        projeto = projeto
                                .replace("\\n", "")
                                .replace("\n", "")
                                .replace(".", "")
                                .trim();

        if(projeto.isBlank()){
            return;
        }

        memoryManager.remember(
                "usuario.projeto",
                projeto,
                MemoryCategory.PROJECT,
                8
        );

    }

    @Override
    public boolean matchesRecall(String message) {

        return message.toLowerCase()
                .contains("projeto");

    }

    @Override
    public MemoryEntry recall(
            LongTermMemory memory
    ) {

        return memory.getEntry(
                "usuario.projeto"
        );

    }

}