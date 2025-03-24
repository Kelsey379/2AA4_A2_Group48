package ca.mcmaster.se2aa4.island.teamXXX.States;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.teamXXX.Action;
import ca.mcmaster.se2aa4.island.teamXXX.Drone;
import ca.mcmaster.se2aa4.island.teamXXX.Island;
import ca.mcmaster.se2aa4.island.teamXXX.MissionControl;
import ca.mcmaster.se2aa4.island.teamXXX.StateMachine;

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
