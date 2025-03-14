package ca.mcmaster.se2aa4.island.teamXXX; 


public class Algorithm{

    public Drone drone; 
    public Island island; 
    public StateMachine currState; 


    public Algorithm(Drone drone,  Island island, StateMachine currState){
        this.drone = drone; 
        this.island = island; 
        this.currState = currState;
    }

    public State deteremineState(StateMachine currState) {
        // if startState: 
            // echo: 
                // if echo range valid: 
                    stateChange.flyForward() // until reach the range 
                // if echo range invalid: 
                    stateChange.changeHeading() 

        // if fly forward: 
            actoin.fly 
        
        changeHeading: 

        // RIGHT RIGHT? 
    }
}