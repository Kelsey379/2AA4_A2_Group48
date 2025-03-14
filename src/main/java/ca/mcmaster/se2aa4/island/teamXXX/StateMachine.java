package ca.mcmaster.se2aa4.island.teamXXX;

import ca.mcmaster.se2aa4.island.teamXXX.States.StartState;


public class StateMachine {
  
    private State currentState;
    private Drone drone;
    private Action currAction;
    private Island island;
    // public List<State> states = new ArrayList<State>();


    public StateMachine(Drone drone, Action action, Island island) {
        this.drone = drone;
        this.currAction = action;
        this.currentState = new StartState(this.drone, this.currAction, this.island);
    }

    public void setState(State state) {
        this.currentState = state;
    }

    public State getState(){
        return this.currentState; 
    }

    // /*public void transitionTo(State state) {
    //     this.currentState = state;
    // }*/

    
}
