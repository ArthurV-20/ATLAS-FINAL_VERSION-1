package br.com.atlas.ai.validation;

import br.com.atlas.ai.AIAction;
import br.com.atlas.ai.AIActionType;

public class ActionValidator {

    public boolean validate(AIAction action) {

        if (action == null) {
            return false;
        }

        if (action.getType() == AIActionType.OPEN_APPLICATION) {
            return true;
        }

        return false;
    }

}