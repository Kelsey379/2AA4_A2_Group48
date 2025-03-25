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

public class EchoCheck extends State {

    private final Logger logger = LogManager.getLogger();

    public EchoCheck(Drone drone, Action action, Island island, StateMachine stateMachine, MissionControl missionControl) {
        super(drone, action, island, stateMachine, missionControl);
    }

    @Override
    public void executeState() {
        String echoAction = drone.echo(drone.getFacingDirection());
        // sets the action that needs to be taken by the drone and called by the takeDecision method.
        missionControl.takeDecision(echoAction);
    }

    @Override
    public State exitState() {
        JSONObject response = missionControl.getResponse();
        Integer cost = response.getInt("cost");
        String status = response.getString("status");
        JSONObject extras = response.getJSONObject("extras");

        int range = extras.getInt("range");
        String echoResult = extras.getString("found");

        drone.updateDrone(cost, status);

        logger.info("EchoCheck: Status = " + status + ", Echo result = " + echoResult + ", Range = " + range);

        if (!"OK".equals(status)) { //check if status has been lost and drone is MIA
            logger.warn("EchoCheck: Status not OK. Transitioning to LossOfSignal.");
            return stateMachine.LossOfSignal;
        }

        if (drone.isBatteryLow()) {
            logger.info("** Battery level is below threshold. Transitioning to GoHome state.");
            return stateMachine.GoHome;  // Transition to GoHome if battery is low
        }

        if ("OUT_OF_RANGE".equals(echoResult)) { //if an ocean is scanned and there's no ground in range, move towards u-turn
            drone.incrementSequentialOutOfRange();
            int count = drone.getSequentialOutOfRange();
        
            logger.info("EchoCheck: OUT_OF_RANGE detected. Count = " + count);
        
            if (count >= 2) { //if out of range is scanned back 2 back, drone has fallen off island
                logger.info("EchoCheck: Two consecutive OUT_OF_RANGE echoes.");
                drone.resetSequentialOutOfRange(); //back 2 back out of range echoes should be reset
                logger.info("Vertical search/Horz conditions met, Flyforward once before IslandEdge.");
                return stateMachine.IslandEdge; 
            }
        
            logger.info("EchoCheck: Single OUT_OF_RANGE echo. Performing UTurn.");
            return stateMachine.UTurn;
        }
        else if ("GROUND".equals(echoResult)) { //In case of an ocean scan followed by echoing ground, fly forward until ground is reached
            drone.resetSequentialOutOfRange();
            island.setRange(range);
            logger.info("EchoCheck: GROUND detected. Resetting OUT_OF_RANGE count. Transitioning to FlyForward.");
            return stateMachine.FlyForward;
        }        
        else {
            //Fallback 
            logger.warn("EchoCheck: Unexpected echo result: " + echoResult + ". Performing UTurn.");
            return stateMachine.UTurn;
        }
    }
}