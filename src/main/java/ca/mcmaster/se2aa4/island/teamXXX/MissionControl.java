package ca.mcmaster.se2aa4.island.teamXXX; 

import org.json.JSONObject;




public class MissionControl{

    String currAction; 
    JSONObject response; 

    public MissionControl(){}

    // setter 
    // this is setCurrentAction
    public void takeDecision(String currAction){
        this.currAction = currAction; 
    }

    //this is getCurrentAction
    public String getTakeDecision(){
        return this.currAction; 
    }

    public void setResponse(JSONObject response){
        this.response = response; 
    }

    public JSONObject getResponse(){
        return this.response; 
    } 
    

    
}