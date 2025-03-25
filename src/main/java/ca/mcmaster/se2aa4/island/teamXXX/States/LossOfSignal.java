package ca.mcmaster.se2aa4.island.teamXXX.States;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;

import ca.mcmaster.se2aa4.island.teamXXX.Action;
import ca.mcmaster.se2aa4.island.teamXXX.Drone;
import ca.mcmaster.se2aa4.island.teamXXX.Island;
import ca.mcmaster.se2aa4.island.teamXXX.MissionControl;
import ca.mcmaster.se2aa4.island.teamXXX.StateMachine;

public class LossOfSignal extends State {

    private final Logger logger = LogManager.getLogger();
    
    public LossOfSignal(Drone drone, Action action, Island island, StateMachine stateMachine, MissionControl missionControl)
    {
        super(drone, action, island, stateMachine, missionControl); 
    }

    @Override
    public void executeState(){
       String resultAction = drone.stop(); 
       // sets the action that needs to be taken by the drone and called by the takeDescision method.
       missionControl.takeDecision(resultAction); 
      

    }

    @Override
    public State exitState(){
        
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

        // this is a rejection state, if ever reached the drone cannot be communicated with anymore and it needs to have no next state 
        return null; 

    }
}
