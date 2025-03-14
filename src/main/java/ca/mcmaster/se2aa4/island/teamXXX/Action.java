package ca.mcmaster.se2aa4.island.teamXXX; 


import org.json.JSONArray;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.teamXXX.enums.Direction;

public class Action implements ActionInterface{

    protected Drone drone; 

    protected Island island; 

    private JSONObject extraInfo; 

    protected JSONObject decision;

    //Add actions to drone class instead, but leave update methods as is

    @Override
    public void updateDrone(JSONObject response){

        String status = response.getJSONObject("status").toString(); 
        Integer cost = response.getInt("cost");
        this.drone.setBattery(cost);  
        
        drone.setStatus(status); 

    }

    @Override 
    public void updateIsland(JSONObject response){
        this.extraInfo = response.getJSONObject("extras");

        JSONArray biomesArray = this.extraInfo.getJSONArray("biomes"); 
        JSONArray creeks = this.extraInfo.getJSONArray("creeks");
        JSONArray sites = this.extraInfo.getJSONArray("sites"); 

        // island.updateBiomesArray() ?? 

        island.setCreeks(creeks);
        island.setSites(sites);  

    }

    @Override 
    public Direction turnLeft(Direction currDir){
        switch(currDir){
            case E: return Direction.N;
            case W: return Direction.S;
            case N: return Direction.W;
            case S: return Direction.E;
        }
        return currDir;
    }

    @Override 
    public Direction turnRight(Direction currDir){
        switch(currDir){
            case E: return Direction.S;
            case W: return Direction.N;
            case N: return Direction.E;
            case S: return Direction.W;
        }
        return currDir;
    }





}