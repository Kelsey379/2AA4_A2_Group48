package ca.mcmaster.se2aa4.island.teamXXX.States;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

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
    public void executeState(){

        String resultAction = drone.stop(); 
        missionControl.takeDecision(resultAction); 

       
    }

    @Override 
    public State exitState(){
        
        JSONObject response = missionControl.getResponse(); 


        Integer cost = response.getInt("cost"); 
        String status = response.getString("status"); 

        drone.updateDrone(cost, status);

        logger.info("** Drone Discoveries: " + drone.getDiscoveries().toString());

        return null; 
    }
    
}
