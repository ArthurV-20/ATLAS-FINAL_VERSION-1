package br.com.atlas.ai.model;

import br.com.atlas.ai.router.IntentType;

public class AIModelSelector {

    public String select(IntentType intent){

        return switch(intent){

            case GREETING,
                 OPEN_APPLICATION,
                 CLOSE_APPLICATION,
                 SEARCH -> "qwen2.5:3b";

            default -> "qwen2.5:7b";

        };

    }

}