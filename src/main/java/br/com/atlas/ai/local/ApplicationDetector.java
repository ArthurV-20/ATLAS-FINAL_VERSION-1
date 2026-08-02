package br.com.atlas.ai.local;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;

public class ApplicationDetector {

    private final Map<String, ApplicationInfo> applications =
            new HashMap<>();


    public ApplicationDetector() {

        register(
                "spotify",
                "Spotify",
                "spotify",
                "música",
                "musica",
                "ouvir música",
                "ouvir musica",
                "ouvi música"
        );

        register(
                "firefox",
                "Firefox",
                "firefox",
                "navegador",
                "browser",
                "internet"
        );

        register(
                "code",
                "Visual Studio Code",
                "vscode",
                "vs code",
                "visual studio code",
                "editor de código",
                "editor de codigo"
        );

    }


    public ApplicationInfo detect(
            String message
    ) {

        String text = normalize(message);

        for (Map.Entry<String, ApplicationInfo> entry
                : applications.entrySet()) {

            if (text.contains(entry.getKey())) {

                return entry.getValue();

            }

        }

        return null;

    }


    private void register(
            String id,
            String displayName,
            String... aliases
    ) {

        ApplicationInfo info =
                new ApplicationInfo(
                        id,
                        displayName
                );

        for (String alias : aliases) {

            applications.put(
                    normalize(alias),
                    info
            );

        }

    }


    private String normalize(
            String text
    ) {

        text =
                Normalizer.normalize(
                        text,
                        Normalizer.Form.NFD
                );

        text =
                text.replaceAll(
                        "\\p{M}",
                        ""
                );

        return text
                .toLowerCase()
                .replaceAll(
                        "[^a-z0-9 ]",
                        " "
                )
                .replaceAll(
                        "\\s+",
                        " "
                )
                .trim();

    }

}