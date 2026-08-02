package br.com.atlas.ai;
import java.util.HashMap;
import java.util.Map;

public class AIAction {

    private final AIActionType type;
    private final Map<String, String> parameters;
    public AIAction(AIActionType type) {
        this.type = type;
        this.parameters = new HashMap<>();
    }
    public AIActionType getType() {
    return  type;

    }
    public Map<String, String> getParameters() {
        return parameters;
    }
    public void addParameter(String key, String value) {
        parameters.put(key, value);
    }
    public String getParameter(String key){
        return parameters.get(key);
    }
}
