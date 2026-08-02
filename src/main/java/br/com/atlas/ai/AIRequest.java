package br.com.atlas.ai;

import br.com.atlas.ai.router.IntentType;

public class AIRequest {

    private final String prompt;
    private final String userMessage;
    private final AIModel model;

    private IntentType intent;

    public AIRequest(
            String prompt,
            String userMessage,
            AIModel model
    ) {
        this.prompt = prompt;
        this.userMessage = userMessage;
        this.model = model;
    }

    public String getPrompt() {
        return prompt;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public AIModel getModel() {
        return model;
    }

    public IntentType getIntent() {
        return intent;
    }

    public void setIntent(IntentType intent) {
        this.intent = intent;
    }

}