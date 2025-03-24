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

    public IslandEdge(Drone drone, Action action, Island island, StateMachine stateMachine, MissionControl missionControl) {
        super(drone, action, island, stateMachine, missionControl);
    }

    @Override
    public void executeState() {
        logger.info("Transitioning to vertical search");
        Direction currentDir = drone.getFacingDirection();
        logger.info("IslandEdge: Current direction: " + currentDir);


        logger.info("has entered islandstate if block");
        Direction newDir = null;

        if (currentDir.equals(Direction.E)) {
            drone.setPrevHorizontalDirection(currentDir);  
            newDir = action.turnLeft(currentDir);          
            logger.info("IslandEdge: Turning LEFT from E to N");
        } 
        else if (currentDir.equals(Direction.W)) {
            drone.setPrevHorizontalDirection(currentDir);  
            newDir = action.turnRight(currentDir);         
            logger.info("IslandEdge: Turning RIGHT from W to N");
        }
        else if (currentDir.equals(Direction.S) && (drone.getPrevHorizontalDirection()).equals(Direction.E)) {  
            newDir = action.turnRight(currentDir);         
            logger.info("IslandEdge: Turning RIGHT from S to W");
        }
        else if (currentDir.equals(Direction.S) && (drone.getPrevHorizontalDirection()).equals(Direction.W)) {
            newDir = action.turnLeft(currentDir);         
            logger.info("IslandEdge: Turning RIGHT from S to E");
        }   
        else if (currentDir.equals(Direction.N) && (drone.getPrevHorizontalDirection()).equals(Direction.W)) {
            newDir = action.turnRight(currentDir);         
            logger.info("IslandEdge: Turning RIGHT from S to E");
        }
        else if (currentDir.equals(Direction.N) && (drone.getPrevHorizontalDirection()).equals(Direction.E)) {
            newDir = action.turnLeft(currentDir);         
            logger.info("IslandEdge: Turning RIGHT from S to W");
        }             
        logger.info("new direction:"+newDir);


        if (newDir != null) {
            drone.setFacingDirection(newDir);
            String currAction = drone.heading(newDir);
            // sets the action that needs to be taken by the drone and called by the takeDescision method.
            missionControl.takeDecision(currAction);
            logger.info("Changing to vertical search");
            return;
        }

        logger.info("IslandEdge: No valid direction to turn!");
    }

    @Override
    public State exitState() {
        JSONObject response = missionControl.getResponse();
        Integer cost = response.getInt("cost");
        String status = response.getString("status");

        drone.updateDrone(cost, status);

        if (drone.isBatteryLow()) {
            logger.info("** Battery level is below threshold. Transitioning to GoHome state.");
            return stateMachine.GoHome;  // Transition to GoHome if battery is low
        }

        if (!status.equals("OK")) {
            logger.warn("IslandEdge: Drone encountered an issue — transitioning to LossOfSignal.");
            return stateMachine.LossOfSignal;
        }

        logger.info("IslandEdge: Turn complete. Facing " + drone.getFacingDirection());
        logger.info("IslandEdge: Previous horizontal direction saved as: " + drone.getPrevHorizontalDirection());

        logger.info("IslandEdge: Transitioning to EchoCheck.");
        return stateMachine.EchoCheck;
    }
}
