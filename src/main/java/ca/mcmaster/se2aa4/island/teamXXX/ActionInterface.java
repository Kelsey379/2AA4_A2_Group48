package ca.mcmaster.se2aa4.island.teamXXX; 

import org.json.JSONObject;


public interface ActionInterface { 
    String fly(); 
    String heading(); 
    String echo(); 
    String scan(); 
    String stop(); 
    void updateDrone(JSONObject response); 
    void updateIsland(JSONObject response); 
    //add methods for movement here
}