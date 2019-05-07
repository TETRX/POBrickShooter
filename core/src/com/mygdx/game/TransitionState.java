package com.mygdx.game;

//A State class to prevent unintentional clicks in-between states

public class TransitionState extends State {
    State nextState;
    protected TransitionState(StateHandler sh, State nextState) {
        super(sh);
        this.sh=sh;
        this.nextState=nextState;
    }
    private float countdown;
    @Override
    public void update(float gameLoopTime) {
        countdown+=gameLoopTime;
        if(countdown>0.1){
            countdown=0;
            sh.add(nextState);
        }
    }
}
