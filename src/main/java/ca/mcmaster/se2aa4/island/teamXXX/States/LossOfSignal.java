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

       missionControl.takeDecision(resultAction); 
      

    }

    @Override
    public State exitState(){
        
        JSONObject response = missionControl.getResponse(); 
       
        Integer cost = response.getInt("cost"); 
        String status = response.getString("status"); 
        drone.updateDrone(cost, status);

        return null; 

    }
}
