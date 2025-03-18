package ca.mcmaster.se2aa4.island.teamXXX.States;


import ca.mcmaster.se2aa4.island.teamXXX.*;
import ca.mcmaster.se2aa4.island.teamXXX.StateMachine;
import org.json.JSONObject;


public class GoHome extends State{
    
    public GoHome(Drone drone, Action action, Island island, StateMachine stateMachine, MissionControl missionControl) {
        super(drone, action, island, stateMachine, missionControl); 
    }

    @Override
    public void executeState(){

        String resultAction = drone.stop(); 
        missionControl.takeDecision(resultAction); 

        JSONObject response = missionControl.getResponse(); 


        Integer cost = response.getInt("cost"); 
        String status = response.getString("status"); 

        drone.updateDrone(cost, status);

    }

    @Override 
    public State exitState(){
        return null; 
    }
    
}
