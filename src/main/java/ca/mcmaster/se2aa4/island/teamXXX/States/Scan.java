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
        missionControl.takeDecision(resultAction);
    }

    @Override
    public State exitState() {

        // 🔁 Reset all detection flags each time we exit this state
        foundCreek = false;
        foundSite = false;
        foundOcean = false;

        JSONObject response = missionControl.getResponse();

        Integer cost = response.getInt("cost");
        String status = response.getString("status");

        drone.updateDrone(cost, status);

        JSONObject extras = response.getJSONObject("extras");
        JSONArray biomes = extras.getJSONArray("biomes");
        JSONArray creeks = extras.getJSONArray("creeks");
        JSONArray sites = extras.getJSONArray("sites");

        logger.info("** Scan received biomes array: " + biomes.toString());

        // Only check the first biome
        if (biomes.length() > 0) {
            String biomeType = biomes.getString(0);
            logger.info("** First biome detected: " + biomeType);

            if ("OCEAN".equals(biomeType)) {
                foundOcean = true;
                logger.info("** Ocean is first biome. Preparing to transition to U-turn.");
            }
        }

        // If not ocean, check for creeks and sites
        if (!foundOcean) {
            foundCreek = creeks.length() > 0;
            foundSite = sites.length() > 0;

            island.updateIsland(foundCreek, foundSite);
        }

        // Transition logic
        if (foundCreek && island.getSites()) {
            logger.info("**Transitioning to GoHome State");
            return stateMachine.GoHome;
        } else if (foundSite && island.getCreek()) {
            logger.info("**Transitioning to GoHome State");
            return stateMachine.GoHome;
        } else if (foundOcean) {
            return stateMachine.UTurn;
        }

        // No creek/site/ocean found – continue flying forward
        return stateMachine.FlyForward;
    }
}
