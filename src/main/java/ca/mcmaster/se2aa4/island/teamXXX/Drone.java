package ca.mcmaster.se2aa4.island.teamXXX; 

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.teamXXX.enums.Direction; 

public class Drone { 
    
    protected Direction currDir; 

    protected Integer currBattery;

    private Action currAction; 

    public JSONObject decision; 
    

    public Drone(Direction startDir, Integer startBattery){

        this.currDir = startDir; 

        this.currBattery = startBattery; 

        this.currAction = new Action(); 
    
    }

    public Direction getHeading(){
        return this.currDir; 
    }

    public Integer getBattery(){
        return this.currBattery; 
    }

    public void setBattery(Integer cost) {
        this.currBattery -= cost; 
    }

    
    public String fly(){
        this.decision.put("action","fly"); 
        return decision.toString(); 
    }

    public String heading(){
        this.decision.put("action","heading");
        return decision.toString(); 
    }

    public String echo(){
        this.decision.put("action","heading");
        return this.decision.toString(); 
    }

    public String scan(){
        this.decision.put("action","scan");
        return this.decision.toString(); 
    }

    public String stop() {
        this.decision.put("action","stop");
        return this.decision.toString(); 
    }

}