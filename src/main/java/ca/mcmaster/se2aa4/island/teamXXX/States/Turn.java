// REDUNDANT --> MOVE TO THE START STATE?? 


package ca.mcmaster.se2aa4.island.teamXXX.States;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.teamXXX.Action; 
import ca.mcmaster.se2aa4.island.teamXXX.Drone;
import ca.mcmaster.se2aa4.island.teamXXX.Island;
import ca.mcmaster.se2aa4.island.teamXXX.MissionControl;
import ca.mcmaster.se2aa4.island.teamXXX.StateMachine;
import ca.mcmaster.se2aa4.island.teamXXX.enums.Direction;


public class Turn extends State {

    private final Logger logger = LogManager.getLogger(); 
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
        
       

        
    }

    @Override
    public State exitState(){
        JSONObject response = missionControl.getResponse(); 
        if (response != null) {
            int cost = response.getInt("cost");
            String status = response.getString("status");
            drone.updateDrone(cost, status);
        
        }
        logger.info("The drone is facing "+currDir);
        logger.info("** StartState: Transitioning to FindGround state.");
        stateMachine.setState(stateMachine.FindGround);
        return stateMachine.getState(); 
    }
    
} 
