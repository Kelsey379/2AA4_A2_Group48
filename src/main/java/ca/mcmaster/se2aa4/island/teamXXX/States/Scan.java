package ca.mcmaster.se2aa4.island.teamXXX.States; 

import org.json.JSONObject;
import org.json.JSONArray; 


import ca.mcmaster.se2aa4.island.teamXXX.*;


public class Scan extends State{

    private boolean foundCreek = false ; 
    private boolean foundSite = false; 
   
    public Scan (Drone drone, Action action, Island island, StateMachine stateMachine){
   
   
        super(drone, action, island, stateMachine); 


    }
   
    @Override
    public void executeState(){

        String resultAction = drone.scan(); 
        missionControl.takeDecision(resultAction); 
        JSONObject response = missionControl.getResponse(); 

        Integer cost = response.getInt("cost");
        String status = response.getString("status"); 

        drone.updateDrone(cost, status);

        JSONArray creeks = response.getJSONArray("creeks");
        JSONArray sites = response.getJSONArray("sites"); 

        if(creeks.length() > 0 ) {
            foundCreek = true; 
        }
        else if (sites.length() > 0 ){
            foundSite = true; 
     
        }
        else{
            foundCreek = false; 
            foundSite = false; 
        }

        island.updateIsland(foundCreek, foundSite);

        
    }

    @Override 
    public State exitState() {

        if(foundCreek){

            if(island.getSites()){
                stateMachine.setState(stateMachine.GoHome);
            }

        }
        else if (foundSite){

            if(island.getCreek()) {
                stateMachine.setState(stateMachine.GoHome);
            }

        }

        else{
            stateMachine.setState(stateMachine.FlyForward);
        }

        return stateMachine.getState(); 
        

    }

}