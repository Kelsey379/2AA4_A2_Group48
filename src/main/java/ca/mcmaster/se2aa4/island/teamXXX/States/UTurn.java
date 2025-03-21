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

    private Direction currDir; 
    private Direction newDir;
    private Direction prevDir;
    private String currAction;
    private int step = 0;
    private final Logger logger = LogManager.getLogger();

    public UTurn(Drone drone, Action action, Island island, StateMachine stateMachine, MissionControl missionControl){
        super(drone, action, island, stateMachine, missionControl);
    }

    @Override
    public void executeState(){
        currDir = drone.getFacingDirection();

        // STEP 1: Set prevDir, turn from E or W to S
        if (step == 0) {
            if (currDir.equals(Direction.E)) {
                prevDir = currDir;
                newDir = action.turnRight(currDir);
            } else if (currDir.equals(Direction.W)) {
                prevDir = currDir;
                newDir = action.turnLeft(currDir);
            }

            if (newDir != null) {
                drone.setFacingDirection(newDir);
                currAction = drone.heading(newDir);
                missionControl.takeDecision(currAction);
                step = 1;
                logger.info("Step 1 of U-Turn: Turned from " + prevDir + " to " + newDir);
                return;
            }
        }

        // STEP 2: Now facing S, turn to opposite of original direction
        if (step == 1 && currDir.equals(Direction.S)) {
            if (prevDir == null) {
                logger.error("prevDir is null during UTurn step 2!");
                return;
            }

            if (prevDir.equals(Direction.W)) {
                newDir = action.turnLeft(currDir);
            } else if (prevDir.equals(Direction.E)) {
                newDir = action.turnRight(currDir);
            }

            if (newDir != null) {
                drone.setFacingDirection(newDir);
                currAction = drone.heading(newDir);
                missionControl.takeDecision(currAction);
                step = 2;
                logger.info("Step 2 of U-Turn: Turned from S to " + newDir + " (opposite of " + prevDir + ")");
            }
        }
    }

    @Override
    public State exitState(){
        JSONObject response = missionControl.getResponse();
        Integer cost = response.getInt("cost"); 
        String status = response.getString("status"); 
        drone.updateDrone(cost, status);

        if (!status.equals("OK")) {
            logger.info("The drone is facing " + drone.getFacingDirection());
            logger.info("Transitioning to LossOfSignal state.");
            return stateMachine.LossOfSignal; 
        }

        if (step == 1) {
            logger.info("U-Turn step 1 complete. Continuing U-turn.");
            return stateMachine.UTurn;
        } else if (step == 2) {
            logger.info("U-Turn complete. Drone now facing " + drone.getFacingDirection());
            // Reset internal state for reuse
            step = 0;
            prevDir = null;
            logger.info("Transition to in Scan state.");
            return stateMachine.Scan;
        }

        // Fallback
        logger.info("Transition to in Scan state.");
        return stateMachine.Scan;
    }
}
