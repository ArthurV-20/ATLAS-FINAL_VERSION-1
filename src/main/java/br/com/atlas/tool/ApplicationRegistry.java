package br.com.atlas.tool;

import java.util.HashMap;
import java.util.Map;

public class ApplicationRegistry {

    private final Map<String, String> applications = new HashMap<>();

    public ApplicationRegistry() {

        register("spotify",
                "spotify",
                "música",
                "musica");

        register("firefox",
                "firefox",
                "navegador",
                "browser",
                "internet");
        register(
                "code",
                "vscode",
                "visual studio code",
                "vs code",
                "editor",
                "codigo",
                "programação"
        );
        register(
                "intellij-idea",
                "intellij",
                "intellij idea",
                "intelij",
                "editor java",
                "editor principal"
        );
                register(
                        "/usr/games/steam",
                        "steam",
                        "jogos",
                        "game",
                        "games"
                );

        register(
                "/snap/bin/discord",
                "discord",
                "chat"
        );
    }

    private void register(
            String executable,
            String... aliases
    ) {

        for (String alias : aliases) {

            applications.put(
                    alias.toLowerCase(),
                    executable
            );

        }

    }

    public String resolve(String application) {

        if (application == null) {
            return null;
        }
        String key = application
                .trim()
                .toLowerCase();

        String result = applications.getOrDefault(key, key);

        System.out.println(
                "[REGISTRY] "
                        + key
                        + " -> "
                        + result
        );

        return result;


    }
    public boolean contains(String alias) {

        if (alias == null) {
            return false;
        }

        return applications.containsKey(
                alias.trim().toLowerCase()
        );
    }
}