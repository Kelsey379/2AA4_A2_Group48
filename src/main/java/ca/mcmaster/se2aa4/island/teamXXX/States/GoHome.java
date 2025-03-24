package ca.mcmaster.se2aa4.island.teamXXX.States;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
        logger.info("** Drone Discoveries: " + drone.getDiscoveries().toString());
        // sets the action that needs to be taken by the drone and called by the takeDescision method.
        missionControl.takeDecision(resultAction); 

       
    }

    @Override 
    public State exitState(){
        // this is an accepting state, the simulation needs to terminate if ever reached, should never have a next state since it goes home
        return null; 
    }
    
}
