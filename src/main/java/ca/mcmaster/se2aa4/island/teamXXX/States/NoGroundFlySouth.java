package ca.mcmaster.se2aa4.island.teamXXX.States;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.teamXXX.Action;
import ca.mcmaster.se2aa4.island.teamXXX.Drone;
import ca.mcmaster.se2aa4.island.teamXXX.Island;
import ca.mcmaster.se2aa4.island.teamXXX.MissionControl;
import ca.mcmaster.se2aa4.island.teamXXX.StateMachine;

public class NoGroundFlySouth extends State {

    private final Logger logger = LogManager.getLogger();

    public NoGroundFlySouth(Drone drone, Action action, Island island, StateMachine state, MissionControl missionControl) {
        super(drone, action, island, state, missionControl);
    }

    @Override
    public void executeState() {

        String currAction = drone.fly(); 
        // sets the action that needs to be taken by the drone and called by the takeDecision method.
        missionControl.takeDecision(currAction);
    }

    @Override
    public State exitState() {
        // gets the acknowledgeResults from the resulting execute state drone actions 
        JSONObject response = missionControl.getResponse();

        Integer cost = response.getInt("cost"); 
        String status = response.getString("status"); 
        
        // update the attributes of the drone 
        drone.updateDrone(cost, status);

        if (!status.equals("OK")) {
            logger.info("The drone is facing " + drone.getFacingDirection());
            logger.info("Transitioning to LossOfSignal state.");
            // if drone signal lost, move to the rejection / termination state 
            return stateMachine.LossOfSignal;
        }
        logger.info("The drone is facing " + drone.getFacingDirection());
        logger.info("Transitioning back to FindGround state.");
        // sets the next 
        return stateMachine.FindGround;
    }
}
