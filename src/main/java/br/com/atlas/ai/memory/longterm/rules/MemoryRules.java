package br.com.atlas.ai.memory.longterm.rules;

import java.util.List;

public final class MemoryRules {

    private MemoryRules() {
    }

    public static List<MemoryRule> getRules() {

        return List.of(
                new IdentityMemoryRule(),
                new ProgrammingLanguageMemoryRule(),
                new ProjectMemoryRule(),
                new FavoriteColorMemoryRule()
        );

    }

}