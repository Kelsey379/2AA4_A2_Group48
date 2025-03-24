package ca.mcmaster.se2aa4.island.teamXXX.States;


import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.teamXXX.Action;
import ca.mcmaster.se2aa4.island.teamXXX.Drone;
import ca.mcmaster.se2aa4.island.teamXXX.Island;
import ca.mcmaster.se2aa4.island.teamXXX.MissionControl;
import ca.mcmaster.se2aa4.island.teamXXX.StateMachine;

public class LossOfSignal extends State {
    
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
        
        JSONObject response = missionControl.getResponse(); 
       
        Integer cost = response.getInt("cost"); 
        String status = response.getString("status"); 
        drone.updateDrone(cost, status);

        // this is a rejection state, if ever reached the drone cannot be communicated with anymore and it needs to have no next state 
        return null; 

    }
}
