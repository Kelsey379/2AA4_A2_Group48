package ca.mcmaster.se2aa4.island.teamXXX.States;


import ca.mcmaster.se2aa4.island.teamXXX.Action; 
import ca.mcmaster.se2aa4.island.teamXXX.Drone;
import ca.mcmaster.se2aa4.island.teamXXX.Island;
import ca.mcmaster.se2aa4.island.teamXXX.MissionControl;
import ca.mcmaster.se2aa4.island.teamXXX.StateMachine;
import ca.mcmaster.se2aa4.island.teamXXX.enums.Direction;


public class Turn extends State {

    Direction currDir;

    public Turn(Drone drone, Action action, Island island, StateMachine state, Direction currDir, MissionControl missionControl) {
        super(drone, action, island, state, missionControl);
        this.currDir = currDir; 
    }
    
    @Override
    public void executeState(){
    
        drone.heading(Direction.S); 
        currDir = Direction.S; 
        
    }

    @Override
    public State exitState(){
        stateMachine.setState(stateMachine.FindGround);
        return stateMachine.getState(); 
    }
    
} 
