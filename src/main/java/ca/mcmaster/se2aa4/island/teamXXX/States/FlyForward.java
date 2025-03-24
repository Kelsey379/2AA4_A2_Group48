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

public class FlyForward extends State {
     
    private int range; 
    private final Logger logger = LogManager.getLogger(); 

    public FlyForward(Drone drone, Action action, Island island, StateMachine stateMachine, MissionControl missionControl){
        super(drone, action, island, stateMachine, missionControl); 
    }

    @Override 
    public void executeState() {
        String resultAction = drone.fly(); 
        missionControl.takeDecision(resultAction); 
    }

    @Override
    public State exitState(){
        JSONObject response = missionControl.getResponse(); 

        range = island.getRange(); 

        Integer cost = response.getInt("cost"); 
        String status = response.getString("status"); 

        drone.updateDrone(cost, status);

        if (!status.equals("OK")) {
            return stateMachine.LossOfSignal; 
        }

        if(drone.getSequentialOutOfRange() == 2){
            Direction dir = drone.getFacingDirection();
            if(drone.getVertSearch() && (dir.equals(Direction.N) || dir.equals(Direction.S))){
                drone.setVertSearch(false);
                logger.info("Vertical search conditions met, Flyforward once before IslandEdge.");
                return stateMachine.FlyForward;
            }
            drone.resetSequentialOutOfRange();
            logger.info("Vertical search/Horz conditions met, Flyforward once before IslandEdge.");
            return stateMachine.IslandEdge;
        }

        drone.resetSequentialOutOfRange();

        if (range > 0) {
            range--;
            island.setRange(range);
            logger.info("Stay in FlyForward state.");
            logger.info("The drone is facing " + drone.getFacingDirection());
            return stateMachine.FlyForward; 
        } else {
            
            island.setHasLandedOnIsland(true); 
            logger.info("The drone is facing " + drone.getFacingDirection());
            logger.info("Transition to Scan state.");
            return stateMachine.Scan; 
        }
    }
}
