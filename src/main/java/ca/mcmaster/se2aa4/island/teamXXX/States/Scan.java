package ca.mcmaster.se2aa4.island.teamXXX.States;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.teamXXX.Action;
import ca.mcmaster.se2aa4.island.teamXXX.Drone;
import ca.mcmaster.se2aa4.island.teamXXX.Island;
import ca.mcmaster.se2aa4.island.teamXXX.MissionControl;
import ca.mcmaster.se2aa4.island.teamXXX.StateMachine;

public class Scan extends State {

    private boolean foundCreek = false;
    private boolean foundSite = false;
    public boolean foundOcean = false;

    private final Logger logger = LogManager.getLogger();

    public Scan(Drone drone, Action action, Island island, StateMachine stateMachine, MissionControl missionControl) {
        super(drone, action, island, stateMachine, missionControl);
    }

    @Override
    public void executeState() {
        String resultAction = drone.scan();
        // sets the action that needs to be taken by the drone and called by the takeDescision method.
        missionControl.takeDecision(resultAction);
    }

    @Override
    public State exitState() {

        foundCreek = false;
        foundSite = false;
        foundOcean = false;

        // acknowledgeResults from the current resulting executestate 

        JSONObject response = missionControl.getResponse();
        Integer cost = response.getInt("cost");
        String status = response.getString("status");

        drone.updateDrone(cost, status);

        JSONObject extras = response.getJSONObject("extras");
        JSONArray biomes = extras.getJSONArray("biomes");
        JSONArray creeks = extras.getJSONArray("creeks");
        JSONArray sites = extras.getJSONArray("sites");

        logger.info("** Scan received biomes array: " + biomes.toString());

        // checks to see if the current scan found a site or a creeks 
        foundCreek = creeks.length() > 0;
        foundSite = sites.length() > 0;
        island.updateIsland(foundCreek, foundSite); 

        // if it found a creels update the discoveries 
        if (foundCreek) {
            String creek = creeks.getString(0);  
            drone.addDiscovery("creeks", creek);  
            logger.info("** Found a creek. Adding to discoveries.");
        }
        // if found sites update the discoveries 
        if (foundSite) {
            String site = sites.getString(0);  
            drone.addDiscovery("sites", site);  
        }

        // does a biome exist? 
        if (biomes.length() > 0) {
            String biomeType = biomes.getString(0);
            logger.info("** First biome detected: " + biomeType);

            // check to see if it found ocean because it concerns deteremining the next state 
            if ("OCEAN".equals(biomeType)) {
                foundOcean = true;
                logger.info("** Ocean detected as first biome.");
            }
        }

        if (drone.isBatteryLow()) {
            logger.info("** Battery level is below threshold. Transitioning to GoHome state.");
            return stateMachine.GoHome;  // Transition to GoHome if battery is low
        }

        // === Decision Logic ===
        if (foundOcean && island.hasLandedOnIsland()) {
            logger.info("** Ocean detected & drone already on island. Going to EchoCheck state.");
            // check if there is an viable ground ahead? 
            return stateMachine.EchoCheck;
        } else if (foundOcean) { //fallback
            // there is no viable ground ahead, and the drone is not on an island anymore ( just ocean )
            logger.info("** Ocean detected before landing. Transitioning to UTurn.");
            // u turn ( not on the same row however but one row down )
            return stateMachine.UTurn;
        }

        logger.info("** Transition to FlyForward state");
        // move to next part of the island 
        return stateMachine.FlyForward;
    }
}
