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


    Direction currDir;
    Boolean lost = false;
    // Flag to indicate that we've issued our initial command
    
    private final Direction echoDir = Direction.E;


    public FindGround(Drone drone, Action action, Island island, StateMachine state, Direction currDir, MissionControl missionControl) {
        super(drone, action, island, state, missionControl);
        this.currDir = currDir; 

    }
    
    @Override
    public void executeState(){
        
            // Issue the initial echo command.
        String resultAction = drone.echo(Direction.E); 
        logger.info("echoing from findground");
        missionControl.takeDecision(resultAction);

    }
    
    @Override
    public State exitState(){
        // Check if a response is available.
        JSONObject response = missionControl.getResponse();
        logger.info("Received response is: " + response);
        if (response == null) {
            logger.error("No response received from mission control.");
            return stateMachine.getState();
        }
        
        // Log the entire response for debugging.
        logger.info("Response received: " + response.toString());
        
        // Process the response.
        Integer cost = response.getInt("cost"); 
        String status = response.getString("status"); 
        JSONObject extras = response.getJSONObject("extras"); 
        
        // Log the extras for debugging.
        logger.info("Extras received: " + extras.toString());
        
        // Check if the range field is present.
        if (!extras.has("range")) {
            logger.error("Range field is missing in the response extras.");
            return stateMachine.getState();
        }
        
        int range = extras.getInt("range");
        logger.info("The range is " + range);

        String found = extras.has("found") ? extras.getString("found") : "";

        drone.updateDrone(cost, status);
    
        if(!status.equals("OK")){
            logger.info("** StartState: Transitioning to LossOfSignal state.");
            return stateMachine.LossOfSignal; 
        }

        missionControl.setResponse(null);

        // Decide next command based on range.
        if (found.equals("GROUND")) {
            // Ground found: adjust heading.
            logger.info("The drone is facing " + drone.getFacingDirection());
            logger.info("Transitioning to FoundGroundTurnEast");
            
            island.setRange(range); 

            return stateMachine.FoundGroundTurnEast;
        } else {
            // Nothing detected: issue fly command.
            logger.info("The drone is facing " + drone.getFacingDirection());
            logger.info("Transitioning to NoGroundFlySouth");
            return stateMachine.NoGroundFlySouth;
        }
    }

} 


