package br.com.atlas.tool;
import br.com.atlas.ai.AIAction;
public interface AtlasTool {
    boolean supports(AIAction action);

    void execute(AIAction action) throws Exception;

}
