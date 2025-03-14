package ca.mcmaster.se2aa4.island.teamXXX;

public abstract class State {

    protected Drone drone; 
    protected Action action; 
    protected Island island; 

    public State (Drone drone, Action action, Island island) {
        this.drone = drone; 
        this.action = action; 
        this.island = island; 
    }
    

    public abstract String executeState();
    public abstract State exitState(); 
    public abstract State exitState(Integer range);
} 