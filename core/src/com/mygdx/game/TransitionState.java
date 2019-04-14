package com.mygdx.game;


public class TransitionState extends State {
    protected TransitionState(StateHandler sh) {
        super(sh);
        this.sh=sh;
    }
    private float countdown;
    @Override
    public void update(float gameLoopTime) {
        countdown+=gameLoopTime;
        if(countdown>0.1){
            countdown=0;
            sh.add(new WaitState(sh));
        }
    }
}
