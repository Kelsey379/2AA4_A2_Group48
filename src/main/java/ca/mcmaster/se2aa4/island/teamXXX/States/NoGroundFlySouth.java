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

public class NoGroundFlySouth extends State {

    private final Logger logger = LogManager.getLogger();

    public NoGroundFlySouth(Drone drone, Action action, Island island, StateMachine state, MissionControl missionControl) {
        super(drone, action, island, state, missionControl);
    }

    @Override
    public void executeState() {
        // Ensure drone is facing SOUTH before flying
        drone.setFacingDirection(Direction.S);

        // Execute fly action (already facing south now)
        String currAction = drone.fly(); 
        missionControl.takeDecision(currAction);
    }

    @Override
    public State exitState() {
        JSONObject response = missionControl.getResponse();

        Integer cost = response.getInt("cost"); 
        String status = response.getString("status"); 

        drone.updateDrone(cost, status);

        if (!status.equals("OK")) {
            logger.info("The drone is facing " + drone.getFacingDirection());
            logger.info("Transitioning to LossOfSignal state.");
            return stateMachine.LossOfSignal;
        }
        logger.info("The drone is facing " + drone.getFacingDirection());
        logger.info("Transitioning back to FindGround state.");
        return stateMachine.FindGround;
    }
}
