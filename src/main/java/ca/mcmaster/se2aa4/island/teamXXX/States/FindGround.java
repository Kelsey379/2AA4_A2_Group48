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

public class FindGround extends State {

    private final Logger logger = LogManager.getLogger(); 

    


    public FindGround(Drone drone, Action action, Island island, StateMachine state, MissionControl missionControl) {
        super(drone, action, island, state, missionControl);

    }
    
    @Override
    public void executeState(){
        
        Direction currDir = drone.getFacingDirection();
        
        String resultAction = drone.echo(action.turnLeft(currDir)); 
        logger.info("echoing from findground");
        // sets the action that needs to be taken by the drone and called by the takeDescision method.
        missionControl.takeDecision(resultAction);

    }
    
    @Override
    public State exitState(){
        // gets the responnse from the acknowledgeResults game engine 
        JSONObject response = missionControl.getResponse();
        logger.info("Received response is: " + response);


        // condiitonal checks to check the validiity of the response and if can be parsed fr certain informatoin 
        if (response == null) {
            logger.error("No response received from mission control.");
            return stateMachine.getState();
        }
        
        logger.info("Response received: " + response.toString());
        
        Integer cost = response.getInt("cost"); 
        String status = response.getString("status"); 
        JSONObject extras = response.getJSONObject("extras"); 
        
        logger.info("Extras received: " + extras.toString());
        
        if (!extras.has("range")) {
            logger.error("Range field is missing in the response extras.");
            return stateMachine.getState();
        }
        
        int range = extras.getInt("range");
        logger.info("The range is " + range);

        String found = extras.has("found") ? extras.getString("found") : "";

        // update the attributes of the dorne 
        drone.updateDrone(cost, status);
    
        if(!status.equals("OK")){
            logger.info("** StartState: Transitioning to LossOfSignal state.");
            return stateMachine.LossOfSignal; 
        }

        // clears the resopnse of the game engine from the previous actions so nothing lingers for next action results 
        missionControl.setResponse(null);

        if (found.equals("GROUND")) {
            logger.info("The drone is facing " + drone.getFacingDirection());
            logger.info("Transitioning to FoundGroundTurnEast");
            
            island.setRange(range); 

            // if found ground, transitions to turning the nose of the drone facing towards the island 
            return stateMachine.FoundGroundTurnEast;
        } else {
            logger.info("The drone is facing " + drone.getFacingDirection());
            logger.info("Transitioning to NoGroundFlySouth");
            // move to the state that continues moving down the island 
            return stateMachine.NoGroundFlySouth;
        }
    }

} 


