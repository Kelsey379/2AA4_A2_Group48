package ca.mcmaster.se2aa4.island.teamXXX; 


import eu.ace_design.island.bot.IExplorerRaid;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Action implements DroneActions{

    private Drone drone; 

    public Action(Drone currDrone){
        self.drone = currDrone; 
    } 

    @Override
    public void fly(){
        self.decision.put("action", "fly"); 
    }

    @Override // will return what headings its facing 
    public void heading() {
        self.decision.put("action","heading");
    }

    @Override // will return json object distance? 
    public void echo(){
        self.decision.put("action","echo"); 
    }

    @Override // will return biome type 
    public void scan(){
        self.decision.put("action","scan"); 
    }

    @Override
    public void stop(){
        self.decision.put("action","stop");
    }

    @Override
    public void updateDrone(JSONObject response){

        
        JSONObject extras = response.getJSONObject("extras"); 

        Integer cost = response.getInt("cost");
        self.drone.setBattery(cost); 
        

        // should the biomeArray, creeks, and the sites be in the drone or should they be stored somewhere else? 
        JSONObject biomesArray = extraInfo.getJSONObject("biomes"); // does this info matter? 
        
        JSONArray creeks = extraInfo.getJSONArray("creeks");
        setCreeks(creeks); 

        JSONArray sites = extraInfo.getJSONArray("sites"); 
        setSites(sites); 

    
    }



}