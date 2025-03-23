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
    private Direction prevHorzDir;
    private String currAction;
    private int step = 0;
    private final Logger logger = LogManager.getLogger();

    public UTurn(Drone drone, Action action, Island island, StateMachine stateMachine, MissionControl missionControl){
        super(drone, action, island, stateMachine, missionControl);
    }

    @Override
    public void executeState(){
        currDir = drone.getFacingDirection();
        prevHorzDir = drone.getPrevHorizontalDirection();

        //STEP 0: First turn from current direction
        if (step == 0) {
            if (currDir.equals(Direction.E)) { //E to S
                prevDir = currDir;
                newDir = action.turnRight(currDir); 
            } else if (currDir.equals(Direction.W)) { //W to S
                prevDir = currDir;
                newDir = action.turnLeft(currDir); 
            } else if (currDir.equals(Direction.N)) {
                prevDir = currDir;
                newDir = (prevHorzDir.equals(Direction.E)) ? action.turnRight(currDir) : action.turnLeft(currDir); //N to E or W
            } else if (currDir.equals(Direction.S)) {
                prevDir = currDir;
                newDir = (prevHorzDir.equals(Direction.E)) ? action.turnLeft(currDir) : action.turnRight(currDir); //S to E or W
            }

            if (newDir != null) {
                drone.setFacingDirection(newDir);
                currAction = drone.heading(newDir);
                missionControl.takeDecision(currAction);
                step = 1;
                return;
            }
        }

        //STEP 1: Turn to face opposite of original direction
        if (step == 1) {
            if (prevDir == null) {
                logger.error("prevDir is null during UTurn step 2!");
                return;
            }

            if(prevDir.equals(Direction.N) && (currDir.equals(Direction.E))){
                newDir = action.turnRight(currDir);
            } else if(prevDir.equals(Direction.N) && currDir.equals(Direction.W)){
                newDir = action.turnLeft(currDir);
            } else if(prevDir.equals(Direction.S) && currDir.equals(Direction.E)){
                newDir = action.turnLeft(currDir);
            } else if(prevDir.equals(Direction.S) && currDir.equals(Direction.W)){
                newDir = action.turnRight(currDir);
            } else if (prevDir.equals(Direction.E)) {
                newDir = action.turnRight(currDir);
            } else if (prevDir.equals(Direction.W)) {
                newDir = action.turnLeft(currDir);
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
    public State exitState(){
        JSONObject response = missionControl.getResponse();
        Integer cost = response.getInt("cost"); 
        String status = response.getString("status"); 
        drone.updateDrone(cost, status);

        if (!status.equals("OK")) {
            logger.info("UTurn: Drone encountered issue, facing " + drone.getFacingDirection());
            logger.info("UTurn: Transitioning to LossOfSignal state.");
            return stateMachine.LossOfSignal; 
        }

        if (step == 1) {
            logger.info("UTurn: Step 1 complete. Drone now facing " + drone.getFacingDirection());
            return stateMachine.UTurn;
        } else if (step == 2) {
            logger.info("UTurn: Completed. Drone now facing " + drone.getFacingDirection());
            //Reset internal state
            step = 0;
            prevDir = null;
            logger.info("UTurn: Transitioning to Scan state.");
            return stateMachine.Scan;
        }

        //Fallback
        logger.warn("UTurn: Unexpected fallback. Returning to Scan.");
        return stateMachine.Scan;
    }
}
