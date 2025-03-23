package ca.mcmaster.se2aa4.island.teamXXX; 

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.teamXXX.enums.Direction; 

public class Drone { 

    private final Logger logger = LogManager.getLogger();
    
    protected Direction currDir; 
    
    public Direction echoDirection; 

    protected Integer currBattery;

    private final Action currAction; 

    public JSONObject decision; 
    public JSONObject parameters; 
    public String status; 

    private int sequentialOutOfRange = 0;


    private Direction prevHorizontalDirection; //store last horz direction prior to beginning verticsl search

    public Drone(Direction startDir, Integer startBattery){

        this.currDir = startDir; 

        this.currBattery = startBattery; 

        this.currAction = new Action(); 

        this.decision = new JSONObject(); 
        this.parameters = new JSONObject();     
    }

    public Action getCurrAction(){
        return this.currAction; 
    }

    public Direction getFacingDirection(){
        return this.currDir; 
    }

    public void setFacingDirection(Direction currDir){
        this.currDir = currDir; 
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
        
        this.currDir = changeDirection;
        this.decision = new JSONObject();
        this.parameters = new JSONObject();
        
        this.decision.put("action", "heading");
        this.parameters.put("direction", changeDirection);
        this.decision.put("parameters", this.parameters);

        
        
        return decision.toString();
    }


    public String echo(Direction echoDirection){

        this.echoDirection = echoDirection; 
        
        this.decision = new JSONObject();
        this.parameters = new JSONObject();
        this.decision.put("action","echo");
        this.parameters.put("direction", this.echoDirection); 
        this.decision.put("parameters", this.parameters); 

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

    public void updateDrone(Integer cost, String status){

        setBattery(cost);  
        
        setStatus(status); 

    }

    public void setPrevHorizontalDirection(Direction dir) {
        this.prevHorizontalDirection = dir;
    }

    public Direction getPrevHorizontalDirection() {
        return this.prevHorizontalDirection;
    }


    public void incrementSequentialOutOfRange() {
        sequentialOutOfRange++;
    }

    public int getSequentialOutOfRange() {
        return sequentialOutOfRange;
    }

    public void resetSequentialOutOfRange() {
        sequentialOutOfRange = 0;
    }


    



}