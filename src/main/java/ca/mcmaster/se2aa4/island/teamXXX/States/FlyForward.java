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
        // sets the action that needs to be taken by the drone and called by the takeDecision method.
        missionControl.takeDecision(resultAction); 
    }

    @Override
    public State exitState(){
        // the response gets the acknowledgeResults ( the result of executing this state )
        JSONObject response = missionControl.getResponse(); 

        range = island.getRange(); 

        Integer cost = response.getInt("cost"); 
        String status = response.getString("status"); 

        // gets the attributes that åre needed to update the status of the drone
        drone.updateDrone(cost, status);


        if (!status.equals("OK")) {
            return stateMachine.LossOfSignal; 
        }

        if (drone.isBatteryLow()) {
            logger.info("** Battery level is below threshold. Transitioning to GoHome state.");
            return stateMachine.GoHome;  // Transition to GoHome if battery is low
        }

        if (range > 0) {
            range--;
            island.setRange(range);
            logger.info("Stay in FlyForward state.");
            logger.info("The drone is facing " + drone.getFacingDirection());
            // found a valid part of the island, continue moving towards it  ( recursive state )
            return stateMachine.FlyForward; 
        } else {
            
            island.setHasLandedOnIsland(true); 
            logger.info("The drone is facing " + drone.getFacingDirection());
            logger.info("Transition to Scan state.");
            // if not on OCEAN biome then continue to scanning this island since it might contain a site or creeks.
            return stateMachine.Scan; 
        }
    }
}
