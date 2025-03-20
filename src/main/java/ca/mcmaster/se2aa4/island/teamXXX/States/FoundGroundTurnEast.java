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

public class FoundGroundTurnEast extends State{
    
    Direction currDir; 
    private final Logger logger = LogManager.getLogger(); 


    public FoundGroundTurnEast(Drone drone, Action action, Island island, StateMachine state, Direction currDir, MissionControl missionControl) {
        super(drone, action, island, state, missionControl); 
        this.currDir = currDir; 
    }

    @Override
    public void executeState() {

        this.currDir = Direction.E; 
        String currAction = drone.heading(currDir); 
        missionControl.takeDecision(currAction);
        
    }

    @Override
    public State exitState(){

        JSONObject response = missionControl.getResponse();
        // if (response == null) {
        //     return stateMachine.getState();
        // }
        
        // Process the response.
        Integer cost = response.getInt("cost"); 
        String status = response.getString("status"); 
        JSONObject extras = response.getJSONObject("extras"); 

        drone.updateDrone(cost, status);
     
        if(!status.equals("OK")){

            logger.info("** StartState: Transitioning to LossOfSignal state.");
            return stateMachine.LossOfSignal; 
 
        }

        // stateMachine.setState(stateMachine.FlyForward); 
        
        // missionControl.setResponse(null); 
        return stateMachine.FlyForward; 


    }

}