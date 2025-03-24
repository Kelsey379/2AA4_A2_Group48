package ca.mcmaster.se2aa4.island.teamXXX; 

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.teamXXX.enums.Direction; 

public class Drone { 

    private final Logger logger = LogManager.getLogger();
    
    protected Direction currDir; 
    
    public Direction echoDirection; 

    protected Integer currBattery;

    private final Action currAction; 

    public Integer threshhold; 
    private boolean isBatteryLow = false; 

    public JSONObject decision; 
    public JSONObject parameters; 
    public String status; 

    private int sequentialOutOfRange = 0;

    private Boolean vertSearch = true;


    private Direction prevHorizontalDirection; //store last horz direction prior to beginning verticsl search

    public JSONObject discoveries = new JSONObject();

    public Drone(Direction startDir, Integer startBattery){

        this.currDir = startDir; 

        this.currBattery = startBattery; 
        this.threshhold = 50; 
        this.currAction = new Action(); 
        this.decision = new JSONObject(); 
        this.parameters = new JSONObject();  
        
        this.discoveries.put("creeks", new JSONArray());
        this.discoveries.put("sites", new JSONArray());
    }

    public JSONObject getDiscoveries() {
        return this.discoveries;
    }

    public void addDiscovery(String type, JSONObject discovery) {
        if (type.equals("creeks")) {
            if (!this.discoveries.has("creeks")) {
                this.discoveries.put("creeks", new JSONArray());
            }
            this.discoveries.getJSONArray("creeks").put(discovery);
            logger.info("** Added creek to discoveries: " + discovery.toString()); // Debugging log
        } else if (type.equals("sites")) {
            if (!this.discoveries.has("sites")) {
                this.discoveries.put("sites", new JSONArray());
            }
            this.discoveries.getJSONArray("sites").put(discovery);
        }
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

    public boolean isBatteryLow() {
        if (this.currBattery.compareTo(this.threshhold) <  0){
            isBatteryLow = true;
            return isBatteryLow; 
        }
        return isBatteryLow; 
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

    //Below are the methods to adjust the last facing direction before beginning vertical search
    public void setPrevHorizontalDirection(Direction dir) {
        this.prevHorizontalDirection = dir;
    }

    public Direction getPrevHorizontalDirection() {
        return this.prevHorizontalDirection;
    }

    //Below are the methods to keep track of falling off the island after horizontal search completion
    public void incrementSequentialOutOfRange() {
        this.sequentialOutOfRange++;
    }

    public int getSequentialOutOfRange() {
        return this.sequentialOutOfRange;
    }

    public void resetSequentialOutOfRange() {
        this.sequentialOutOfRange = 0;
    }

    public Boolean getVertSearch(){
        return this.vertSearch;
    }

    public void setVertSearch(Boolean vertSearch){
        this.vertSearch = vertSearch;
    }

    


    



}