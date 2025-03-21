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
        // Directly set drone's facing direction
        drone.setFacingDirection(Direction.E);
        
        String currAction = drone.heading(Direction.E); 
        missionControl.takeDecision(currAction);
    }

    @Override
    public State exitState() {
        JSONObject response = missionControl.getResponse();
        
        Integer cost = response.getInt("cost"); 
        String status = response.getString("status"); 
        drone.updateDrone(cost, status);

        if (!status.equals("OK")) {
            logger.info("Transitioning to LossOfSignal state.");
            return stateMachine.LossOfSignal; 
        }

        logger.info("Transitioning to FlyForward state.");
        return stateMachine.FlyForward; 
    }
}
