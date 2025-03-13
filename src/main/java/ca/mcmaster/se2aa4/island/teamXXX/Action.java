package ca.mcmaster.se2aa4.island.teamXXX; 


import org.json.JSONArray;
import org.json.JSONObject;

public class Action implements ActionInterface{

    protected Drone drone; 

    protected Island island; 

    private JSONObject extraInfo; 

    protected JSONObject decision;

    //Add actions to drone class instead, but leave update methods as is
    @Override
    public String fly(){
        decision.put("action", "fly");
        return decision.toString(); 
        
    }

    @Override // will return what headings its facing 
    public String heading() {
        decision.put("action","heading");
        return decision.toString(); 
    }

    @Override // will return json object distance? 
    public String echo(){
        this.decision.put("action","echo"); 
        return decision.toString(); 
    }

    @Override // will return biome type 
    public String scan(){
        this.decision.put("action","scan"); 
        return decision.toString(); 
    }

    @Override
    public String stop(){
        this.decision.put("action","stop");
        return decision.toString(); 
    }

    @Override
    public void updateDrone(JSONObject response){

        Integer cost = response.getInt("cost");
        this.drone.setBattery(cost);  

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





}