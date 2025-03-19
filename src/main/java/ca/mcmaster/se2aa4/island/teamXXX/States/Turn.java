// REDUNDANT --> MOVE TO THE START STATE?? 


package ca.mcmaster.se2aa4.island.teamXXX.States;


import org.json.JSONObject;

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

        if(currDir == Direction.E){
            currDir = action.turnRight(currDir); 
        }

        String currAction = drone.heading(currDir); 
        missionControl.takeDecision(currAction);
        
        JSONObject response = missionControl.getResponse(); 

        Integer cost = response.getInt("cost"); 
        String status = response.getString("status"); 
        drone.updateDrone(cost, status);

        
    }

    @Override
    public State exitState(){
        stateMachine.setState(stateMachine.FindGround);
        return stateMachine.getState(); 
    }
    
} 
