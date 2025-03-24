package ca.mcmaster.se2aa4.island.teamXXX; 

import org.json.JSONObject;


// acts as the bridge between the explorer class and the states / machine

public class MissionControl{

    String currAction; 
    JSONObject response; 



    public MissionControl(){}

    // method that will be called by takeDecision in the explorer class
    public void takeDecision(String currAction){
        this.currAction = currAction; 
    }


    public String getTakeDecision(){
        return this.currAction; 
    }

    // after the action of the drone is taken, and the engine calls the ackresults, ackresults will call set response that will be used 
    // by the state executeState methods 
    public void setResponse(JSONObject response){
        this.response = response; 
    }

    public JSONObject getResponse(){
        return this.response; 
    } 
    

    
}