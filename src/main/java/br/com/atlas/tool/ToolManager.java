package br.com.atlas.tool;

import br.com.atlas.ai.AIAction;
import br.com.atlas.tool.impl.OpenApplicationTool;

import java.util.ArrayList;
import java.util.List;

public class ToolManager {

    private final List<AtlasTool> tools = new ArrayList<>();

    public ToolManager() {

        register(new OpenApplicationTool());

        // Futuras ferramentas
        // register(new CloseApplicationTool());
        // register(new OpenUrlTool());
        // register(new SearchWebTool());
        // register(new ReadFileTool());
        // register(new WriteFileTool());

    }

    private void register(AtlasTool tool) {
        tools.add(tool);
    }

    public void execute(AIAction action) throws Exception {

        for (AtlasTool tool : tools) {

            if (tool.supports(action)) {
                tool.execute(action);
                return;
            }

        }

        throw new IllegalArgumentException(
                "Nenhuma ferramenta encontrada para: "
                        + action.getType()
        );
    }
}