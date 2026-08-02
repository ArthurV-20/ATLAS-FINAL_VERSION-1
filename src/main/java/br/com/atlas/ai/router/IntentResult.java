package br.com.atlas.ai.router;

import java.util.HashMap;
import java.util.Map;

public class IntentResult {

    private final IntentType type;
    private final Map<String,String> parameters = new HashMap<>();


    public IntentResult(IntentType type){
        this.type = type;
    }


    public IntentType getType(){
        return type;
    }


    public Map<String,String> getParameters(){
        return parameters;
    }


    public void addParameter(
            String key,
            String value
    ){
        parameters.put(key,value);
    }

}