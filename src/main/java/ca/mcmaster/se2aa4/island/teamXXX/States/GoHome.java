package ca.mcmaster.se2aa4.island.teamXXX.States;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;

import ca.mcmaster.se2aa4.island.teamXXX.Action;
import ca.mcmaster.se2aa4.island.teamXXX.Drone;
import ca.mcmaster.se2aa4.island.teamXXX.Island;
import ca.mcmaster.se2aa4.island.teamXXX.MissionControl;
import ca.mcmaster.se2aa4.island.teamXXX.StateMachine;


public class GoHome extends State{

    private final Logger logger = LogManager.getLogger();
    
    public GoHome(Drone drone, Action action, Island island, StateMachine stateMachine, MissionControl missionControl) {
        super(drone, action, island, stateMachine, missionControl); 
    }

    @Override
    public void executeState() {

        String resultAction = drone.stop(); 
        logger.info("** Drone Discoveries:");

        // Get the "creeks" and "sites" arrays from the discoveries object
        JSONArray creeks = drone.getDiscoveries().getJSONArray("creeks");
        JSONArray sites = drone.getDiscoveries().getJSONArray("sites");

        // Log each creek line by line
        logger.info("** Creeks:");
        for (int i = 0; i < creeks.length(); i++) {
            logger.info("  " + creeks.getString(i));
        }

        // Log each site line by line
        logger.info("** Sites:");
        for (int i = 0; i < sites.length(); i++) {
            logger.info("  " + sites.getString(i));
        }

        missionControl.takeDecision(resultAction); 
    }


    @Override 
    public State exitState(){
        // this is an accepting state, the simulation needs to terminate if ever reached, should never have a next state since it goes home
        return null; 
    }
    
}
