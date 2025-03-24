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

public class FoundGroundTurnEast extends State {

    private final Logger logger = LogManager.getLogger();

    public FoundGroundTurnEast(Drone drone, Action action, Island island, StateMachine state, MissionControl missionControl) {
        super(drone, action, island, state, missionControl);
    }

    @Override
    public void executeState() {
        drone.setFacingDirection(Direction.E);
        
        String currAction = drone.heading(Direction.E); 
        // sets the action that needs to be taken by the drone and called by the takeDescision method.
        missionControl.takeDecision(currAction);
    }

    @Override
    public State exitState() {
        // gets acknowledgeResults from the resulting exeucte state 
        JSONObject response = missionControl.getResponse();
        
        Integer cost = response.getInt("cost"); 
        String status = response.getString("status"); 

        // updates drone attributes
        drone.updateDrone(cost, status);

        // conditional checks to determeine the validiity of the drone and based off that determeine the next states 

        if (!status.equals("OK")) {
            logger.info("The drone is facing " + drone.getFacingDirection());
            logger.info("Transitioning to LossOfSignal state.");
            return stateMachine.LossOfSignal; 
        }
        logger.info("The drone is facing " + drone.getFacingDirection());
        logger.info("Transitioning to FlyForward state.");
        // found island, status is fine, move towards the island
        return stateMachine.FlyForward; 
    }
}
