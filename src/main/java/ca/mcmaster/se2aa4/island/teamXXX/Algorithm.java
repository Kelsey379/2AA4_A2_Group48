package ca.mcmaster.se2aa4.island.teamXXX; 


public class Algorithm{

    public Drone drone; 
    public Island island; 
    public StateMachine currState; 


    public Algorithm(Drone drone, Action actoin, Island island, StateMachine currState, Direction startDir){
        this.drone = drone; 
        this.island = island; 
        this.currState = currState;
    }


    
}