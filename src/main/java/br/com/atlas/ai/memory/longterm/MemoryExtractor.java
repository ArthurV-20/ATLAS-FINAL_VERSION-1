package br.com.atlas.ai.memory.longterm;

import br.com.atlas.ai.memory.longterm.rules.MemoryRule;
import br.com.atlas.ai.memory.longterm.rules.MemoryRules;

import java.util.List;

public class MemoryExtractor {

    private final List<MemoryRule> rules =
            MemoryRules.getRules();

    public void analyze(
            String message,
            MemoryManager memoryManager
    ) {
        System.out.println(
                "[MEMORY EXTRACTOR] Recebido: "
                        + message);


        if (message == null || message.isBlank()) {
            return;
        }

        for (MemoryRule rule : rules) {
            System.out.println(
                    "[MEMORY RULE] Testando: "
                            + rule.getClass().getSimpleName()
            );

                if (rule.matchesSave(message)) {
                    System.out.println(
                            "[MEMORY RULE] Ativada: "
                                    + rule.getClass().getSimpleName()
                    );
                    rule.save(
                            message,
                            memoryManager
                    );

                }

        }
    }

}