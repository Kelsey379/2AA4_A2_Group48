package ca.mcmaster.se2aa4.island.teamXXX; 


import eu.ace_design.island.bot.IExplorerRaid;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Action implements DroneActions{

    protected Drone drone; 

    protected Island island; 

    private JSONObject extraInfo; 

    public Action(Drone currDrone){
        this.drone = currDrone; 
    } 

    @Override
    public void fly(){
        this.decision.put("action", "fly"); 
    }

    @Override // will return what headings its facing 
    public void heading() {
        this.decision.put("action","heading");
    }

    @Override // will return json object distance? 
    public void echo(){
        this.decision.put("action","echo"); 
    }

    @Override // will return biome type 
    public void scan(){
        this.decision.put("action","scan"); 
    }

    @Override
    public void stop(){
        this.decision.put("action","stop");
    }

    @Override
    public void updateDrone(JSONObject response){

        Integer cost = response.getInt("cost");
        this.drone.setBattery(cost); 
        

        // should the biomeArray, creeks, and the sites be in the drone or should they be stored somewhere else? 
        // JSONObject biomesArray = extraInfo.getJSONObject("biomes"); // does this info matter? 
        
        // JSONArray creeks = extraInfo.getJSONArray("creeks");
        // setCreeks(creeks); 

        // JSONArray sites = extraInfo.getJSONArray("sites"); 
        // setSites(sites); 

    }

    @Override 
    public void updateIsland(JSONObject response){
        this.extraInfo = response.getJSONObject("extras");

        JSONObject biomesArray = this.extraInfo.getJSONArray("biomes"); 
        JSONArray creeks = this.extraInfo.getJSONArray("creeks");
        JSONArray sites = this.extraInfo.getJSONArray("sites"); 

        // island.updateBiomesArray() ?? 

        this.island.setCreeks(JSONArray creeks);
        this.island.setSites(JSONArray sites);  

    }





}