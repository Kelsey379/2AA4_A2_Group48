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

public class IslandEdge extends State {

    private final Logger logger = LogManager.getLogger();
    Direction currentDir;
    Direction newDir;

    public IslandEdge(Drone drone, Action action, Island island, StateMachine stateMachine, MissionControl missionControl) {
        super(drone, action, island, stateMachine, missionControl);
    }

    @Override
    public void executeState() {
        currentDir = drone.getFacingDirection();
        logger.info("IslandEdge: Current direction: " + currentDir);

        if (currentDir.equals(Direction.E)) {
            drone.setPrevHorizontalDirection(currentDir); // Save direction before turning
            newDir = action.turnLeft(currentDir); // Turn left to face North
        } 
        else if (currentDir.equals(Direction.W)) {
            drone.setPrevHorizontalDirection(currentDir); // Save direction before turning
            newDir = action.turnRight(currentDir); // Turn right to face North
        }

        if (newDir != null) {
            drone.setFacingDirection(newDir);
            String decision = drone.heading(newDir);
            logger.info("IslandEdge: Issuing heading decision: " + decision);
            missionControl.takeDecision(decision);
        } else {
            logger.warn("IslandEdge: newDir is null! No heading issued.");
        }
    }

    @Override
    public State exitState() {
        JSONObject response = missionControl.getResponse();
        Integer cost = response.getInt("cost");
        String status = response.getString("status");

        drone.updateDrone(cost, status);
        logger.info("The drone now faces " + drone.getFacingDirection());
        logger.info("Transitioning to EchoCheck State.");
        return stateMachine.EchoCheck;
    }
}
