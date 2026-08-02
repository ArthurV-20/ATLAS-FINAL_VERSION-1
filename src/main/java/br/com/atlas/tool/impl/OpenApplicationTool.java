package br.com.atlas.tool.impl;

import br.com.atlas.ai.AIAction;
import br.com.atlas.ai.AIActionType;
import br.com.atlas.ai.AIParameters;
import br.com.atlas.task.impl.OpenAppTask;
import br.com.atlas.tool.ApplicationRegistry;
import br.com.atlas.tool.AtlasTool;

public class OpenApplicationTool implements AtlasTool {

    private final ApplicationRegistry registry =
            new ApplicationRegistry();

    @Override
    public boolean supports(AIAction action) {
        return action.getType() == AIActionType.OPEN_APPLICATION;
    }

    @Override
    public void execute(AIAction action) throws Exception {

        String application =
                action.getParameter(AIParameters.APPLICATION);

        if (application == null || application.isBlank()) {
            throw new IllegalArgumentException(
                    "Nenhuma aplicação foi informada."
            );
        }

        System.out.println("[TOOL] Aplicação solicitada: " + application);

        String executable =
                registry.resolve(application);

        System.out.println("[TOOL] Executável: " + executable);

        new OpenAppTask(executable).execute();

    }
}