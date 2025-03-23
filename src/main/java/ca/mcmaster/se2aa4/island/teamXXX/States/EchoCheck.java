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
    private int sequentialOceanEchoes = 0;

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
        String echoResult = extras.getString("found");

        drone.updateDrone(cost, status);

        logger.info("EchoCheck: Status = " + status + ", Echo result = " + echoResult + ", Range = " + range);

        if (!"OK".equals(status)) {
            logger.warn("EchoCheck: Status not OK. Transitioning to LossOfSignal.");
            return stateMachine.LossOfSignal;
        }

        if ("OCEAN".equals(echoResult)) {
            sequentialOceanEchoes++;
            logger.info("EchoCheck: Ocean detected. sequentialOceanEchoes = " + sequentialOceanEchoes);

            if (sequentialOceanEchoes >= 2) {
                logger.info("EchoCheck: Two consecutive ocean echoes. Transitioning to IslandEdge.");
                sequentialOceanEchoes = 0;
                return stateMachine.IslandEdge;
            }

            logger.info("EchoCheck: Ocean detected, but not enough to confirm island edge. UTurning.");
            return stateMachine.UTurn;
        } else if ("GROUND".equals(echoResult)) {
            sequentialOceanEchoes = 0;
            island.setRange(range);
            logger.info("EchoCheck: Ground detected. Resetting ocean count. Transitioning to FlyForward.");
            return stateMachine.FlyForward;
        } else {
            //fallback
            logger.warn("EchoCheck: Unexpected biome type detected: " + echoResult + ". UTurning.");
            return stateMachine.UTurn;
        }
    }
}
