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

    public Turn(Drone drone, Action action, Island island, StateMachine state, MissionControl missionControl) {
        super(drone, action, island, state, missionControl);
    }

    @Override
    public void executeState() {
        
        Direction currentDirection = drone.getFacingDirection();

       
        Direction newDirection = action.turnRight(currentDirection);

        
        drone.setFacingDirection(newDirection);

        
        logger.info("Drone turned from {} to {}", currentDirection, newDirection);

        
        String currAction = drone.heading(newDirection);
        missionControl.takeDecision(currAction);
    }

    @Override
    public State exitState() {
        JSONObject response = missionControl.getResponse();
        if (response != null) {
            int cost = response.getInt("cost");
            String status = response.getString("status");
            drone.updateDrone(cost, status);
        }

        logger.info("The drone is now facing {}", drone.getFacingDirection());
        logger.info("**Transitioning to FindGround state.");

        return stateMachine.FindGround;
    }
}
