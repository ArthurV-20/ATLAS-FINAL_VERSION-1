package br.com.atlas.ai.memory.longterm.rules;

import br.com.atlas.ai.memory.longterm.LongTermMemory;
import br.com.atlas.ai.memory.longterm.MemoryCategory;
import br.com.atlas.ai.memory.longterm.MemoryEntry;
import br.com.atlas.ai.memory.longterm.MemoryManager;

public class IdentityMemoryRule implements MemoryRule {

    @Override
    public boolean matchesSave(String message) {

        String lower = message.toLowerCase();

        return lower.contains("meu nome é")
                || lower.contains("me chamo")
                || lower.startsWith("sou ")
                || lower.contains("pode me chamar de");

    }

    @Override
    public void save(
            String message,
            MemoryManager memoryManager
    ) {

        String lower = message.toLowerCase();

        String nome;

        if (lower.contains("meu nome é")) {

            nome = message.substring(
                    lower.indexOf("meu nome é")
                            + "meu nome é".length()
            );

        } else if (lower.contains("me chamo")) {

            nome = message.substring(
                    lower.indexOf("me chamo")
                            + "me chamo".length()
            );

        } else if (lower.startsWith("sou ")) {

            nome = message.substring(4);

        } else {

            nome = message.substring(
                    lower.indexOf("pode me chamar de")
                            + "pode me chamar de".length()
            );

        }
        nome = nome
                .replace("\\n", "")
                .replace("\n", "")
                .replace(".", "")
                .trim();

        if (nome.isBlank()) {
            return;
        }

        memoryManager.remember(
                "usuario.nome",
                nome,
                MemoryCategory.IDENTITY,
                10
        );

    }

    @Override
    public boolean matchesRecall(String message) {

        String lower =
                message.toLowerCase();

        return lower.contains("nome")
                || lower.contains("chamar")
                || lower.contains("quem sou");

    }

    @Override
    public MemoryEntry recall(
            LongTermMemory memory
    ) {

        return memory.getEntry("usuario.nome");

    }

}