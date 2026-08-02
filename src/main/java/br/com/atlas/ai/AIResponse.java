package br.com.atlas.ai;

import java.util.List;
import java.util.ArrayList;

public class AIResponse {

    private final String message;
    private final List<AIAction> actions;

    public AIResponse(String message) {
        this.message = message;
        this.actions = new ArrayList<>();
    }

    public String getMessage() {
        return message;
    }
    public List<AIAction> getActions() {
        return List.copyOf(actions);
    }
    public void addAction(AIAction action) {
        actions.add(action);
    }
}