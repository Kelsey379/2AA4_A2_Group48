package ca.mcmaster.se2aa4.island.teamXXX; 

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.teamXXX.enums.Direction; 

public class Drone { 
    
    protected Direction currDir; 

    protected Integer currBattery;

    private Action currAction; 

    public JSONObject decision; 
    public JSONObject parameters; 
    public String status; 
    

    public Drone(Direction startDir, Integer startBattery){

        this.currDir = startDir; 

        this.currBattery = startBattery; 

        this.currAction = new Action(); 
    
    }

    public Action getCurrAction(){
        return this.currAction; 
    }

    public Direction getFacingDirection(){
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

    public String heading(Direction changeDirection){
        this.decision.put("action","heading");
        parameters.put("direction", changeDirection); 
        decision.put("parameters",parameters); 

    
        return decision.toString(); 
    }

    public String echo(Direction echoDirection){

        this.decision.put("action","echo");
        // this.direction.NORTH --> "N"; 
        parameters.put("direction", echoDirection); 
        decision.put("parameteres",parameters); 

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

    public void setStatus(String status){
        this.status = status; 
    }


    public String getStatus(){
        return status; 
    }

    public void turnRight(Direction currDir) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void updateDrone(Integer cost, String Status){

        setBattery(cost);  
        
        setStatus(status); 

    }



}