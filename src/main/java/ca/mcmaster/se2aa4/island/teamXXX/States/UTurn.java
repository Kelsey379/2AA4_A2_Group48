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

public class UTurn extends State {

    private final Logger logger = LogManager.getLogger();
    private int step = 0; // Step tracker to manage 2-part turn

    public UTurn(Drone drone, Action action, Island island, StateMachine state, MissionControl missionControl) {
        super(drone, action, island, state, missionControl);
    }

    @Override
    public void executeState() {
        Direction currentDir = drone.getFacingDirection();

        if (step == 0) {
            // First turn → always turn South
            String action = drone.heading(Direction.S);
            drone.setFacingDirection(Direction.S);
            missionControl.takeDecision(action);
            logger.info("UTurn step 0: Turning South");
        } else if (step == 1) {
            // Second turn → either turn East or West
            Direction newDir = (currentDir == Direction.S || currentDir == Direction.E) ? Direction.W : Direction.E;
            String action = drone.heading(newDir);
            drone.setFacingDirection(newDir);
            missionControl.takeDecision(action);
            logger.info("UTurn step 1: Turning {}", newDir);
        }
    }

    @Override
    public State exitState() {
        JSONObject response = missionControl.getResponse();
        int cost = response.getInt("cost");
        String status = response.getString("status");
        drone.updateDrone(cost, status);

        if (!"OK".equals(status)) {
            logger.warn("UTurn failed. Drone lost signal.");
            return stateMachine.LossOfSignal;
        }

        if (step < 1) {
            step++;
            logger.info("UTurn continuing to second step...");
            return stateMachine.UTurn; // Stay in UTurn state to complete second turn
        }

        logger.info("UTurn complete. Transitioning to FlyForward.");
        return stateMachine.FlyForward;
    }
}
