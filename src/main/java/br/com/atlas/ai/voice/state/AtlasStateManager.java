package br.com.atlas.ai.voice.state;


public class AtlasStateManager {

    private AtlasState currentState;


    public AtlasStateManager() {
        this.currentState = AtlasState.IDLE;
    }


    public AtlasState getState() {
        return currentState;
    }


    public void changeState(AtlasState newState) {

        System.out.println(
                "ATLAS State: "
                        + currentState
                        + " -> "
                        + newState
        );

        this.currentState = newState;
    }

}