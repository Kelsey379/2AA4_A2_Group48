package ca.mcmaster.se2aa4.island.teamXXX.States;

import ca.mcmaster.se2aa4.island.teamXXX.Action;
import ca.mcmaster.se2aa4.island.teamXXX.Drone;
import ca.mcmaster.se2aa4.island.teamXXX.Island;
import ca.mcmaster.se2aa4.island.teamXXX.MissionControl;
import ca.mcmaster.se2aa4.island.teamXXX.StateMachine; 

public abstract class State {

    protected Drone drone; 
    protected Action action; 
    protected Island island; 
    protected StateMachine stateMachine; 
    protected MissionControl missionControl; 

    public State (Drone drone, Action action, Island island, StateMachine stateMachine, MissionControl missionControl) {
        this.drone = drone; 
        this.action = action; 
        this.island = island; 
        this.stateMachine = stateMachine; 
        this.missionControl = missionControl; 
    }
    

    public abstract void executeState();
    public abstract State exitState(); 

} 