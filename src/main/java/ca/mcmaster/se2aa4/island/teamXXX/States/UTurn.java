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
    public void executeState() {
        currDir = drone.getFacingDirection();

        // STEP 0: Save current direction, make 90° turn
        if (step == 0) {
            prevDir = currDir;
            Direction prevHorizontal = drone.getPrevHorizontalDirection();

            switch (currDir) {
                case E:
                    newDir = action.turnRight(currDir); // → S
                    break;
                case W:
                    newDir = action.turnLeft(currDir); // → S
                    break;
                case N:
                    if (prevHorizontal == Direction.E) {
                        newDir = action.turnRight(currDir); // N → E
                    } else if (prevHorizontal == Direction.W) {
                        newDir = action.turnLeft(currDir); // N → W
                    }
                    break;
                case S:
                    if (prevHorizontal == Direction.E) {
                        newDir = action.turnLeft(currDir); // S → E
                    } else if (prevHorizontal == Direction.W) {
                        newDir = action.turnRight(currDir); // S → W
                    }
                    break;
                default:
                    logger.warn("UTurn: Unhandled direction " + currDir);
                    break;
            }

            if (newDir != null) {
                drone.setFacingDirection(newDir);
                currAction = drone.heading(newDir);
                missionControl.takeDecision(currAction);
                step = 1;
                return;
            }
        }

        // STEP 1: Now turn 90° again to complete U-turn
        if (step == 1) {
            currDir = drone.getFacingDirection();

            switch (prevDir) {
                case E:
                case W:
                    newDir = Direction.S;
                    break;
                case N:
                case S:
                    newDir = Direction.N;
                    break;
            }

            if (newDir != null) {
                drone.setFacingDirection(newDir);
                currAction = drone.heading(newDir);
                missionControl.takeDecision(currAction);
                step = 2;
            }
        }
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

        if (step == 1) {
            logger.info("The drone is facing " + drone.getFacingDirection());
            logger.info("U-Turn step 1 complete. Continuing U-turn.");
            return stateMachine.UTurn;
        } else if (step == 2) {
            logger.info("U-Turn complete. Drone now facing " + drone.getFacingDirection());
            step = 0;
            prevDir = null;
            return stateMachine.Scan;
        }

        return stateMachine.Scan;
    }
}
