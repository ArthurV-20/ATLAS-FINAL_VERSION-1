package br.com.atlas.ai.memory.longterm.rules;

import br.com.atlas.ai.memory.longterm.*;

public class FavoriteColorMemoryRule
        implements MemoryRule {

    @Override
    public boolean matchesSave(String message) {

        return message.toLowerCase()
                .contains("minha cor favorita é");

    }

    @Override
    public void save(
            String message,
            MemoryManager memoryManager
    ) {

        String lower =
                message.toLowerCase();

        String cor =
                message.substring(
                        lower.indexOf("minha cor favorita é")
                                + "minha cor favorita é".length()
                ).trim();

                cor = cor
                .replace("\\n", "")
                .replace("\n", "")
                .replace(".", "")
                .trim();

        if(cor.isBlank()){
            return;
        }

        memoryManager.remember(
                "usuario.cor_favorita",
                cor,
                MemoryCategory.PREFERENCE,
                5
        );

    }

    @Override
    public boolean matchesRecall(String message) {

        return message.toLowerCase()
                .contains("cor");

    }

    @Override
    public MemoryEntry recall(
            LongTermMemory memory
    ) {

        return memory.getEntry(
                "usuario.cor_favorita"
        );

    }

}