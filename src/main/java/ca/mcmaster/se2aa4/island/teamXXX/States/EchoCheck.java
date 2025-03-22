package ca.mcmaster.se2aa4.island.teamXXX.States;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.teamXXX.Action;
import ca.mcmaster.se2aa4.island.teamXXX.Drone;
import ca.mcmaster.se2aa4.island.teamXXX.Island;
import ca.mcmaster.se2aa4.island.teamXXX.MissionControl;
import ca.mcmaster.se2aa4.island.teamXXX.StateMachine;

public class EchoCheck extends State {

    private final Logger logger = LogManager.getLogger();

    public EchoCheck(Drone drone, Action action, Island island, StateMachine stateMachine, MissionControl missionControl) {
        super(drone, action, island, stateMachine, missionControl);
    }

    @Override
    public void executeState() {
        String echoAction = drone.echo(drone.getFacingDirection());
        missionControl.takeDecision(echoAction);
    }

    @Override
    public State exitState() {
        JSONObject response = missionControl.getResponse();
        Integer cost = response.getInt("cost");
        String status = response.getString("status");
        JSONObject extras = response.getJSONObject("extras"); 

        int range = extras.getInt("range");
        logger.info("The range is " + range);

        drone.updateDrone(cost, status);

        if (!status.equals("OK")) {
            logger.info("EchoCheck: Status not OK. Transitioning to LossOfSignal.");
            return stateMachine.LossOfSignal;
        }

        String echoResult = response.getJSONObject("extras").getString("found");
        logger.info("EchoCheck: Echo result = " + echoResult);

        if (!"GROUND".equals(echoResult)) {
            logger.info("EchoCheck: End of island detected. Transitioning to UTurn.");
            return stateMachine.UTurn;
        } else {
            logger.info("EchoCheck: Still ground ahead. Transitioning to FlyForward.");
            island.setRange(range);
            return stateMachine.FlyForward;
        }
    }
}
