package ca.mcmaster.se2aa4.island.teamXXX;

public abstract class State {


    public State(Drone drone, Action action){ // will have the drone and the action it is performing
        this.drone = drone; 
        this.action = action; 
    }

    public void enterState(){

    }

    public void exitState(){

    }

    
}
