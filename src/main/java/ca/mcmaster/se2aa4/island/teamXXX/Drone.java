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


    private Direction prevHorizontalDirection; //store last horizontal direction prior to beginning vertical search

    public JSONObject discoveries = new JSONObject();

    // constructor for the drone and all the attributes that are important for the operation of the drone 

    public Drone(Direction startDir, Integer startBattery){

        this.currDir = startDir; 
        this.currBattery = startBattery; 
        //  the above 2 attributes are passed as parameters when it is initialized
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

    public void addDiscovery(String type, String discovery) {
        //Check if the discovery is already in creeks or sites
        JSONArray discoveryArray = this.discoveries.getJSONArray(type);
    
        //Avoid adding the discovery if it already exists
        if (!discoveryArray.toList().contains(discovery)) {
            discoveryArray.put(discovery);
            logger.info("** Added " + type + " to discoveries: " + discovery); 
        } else {
            logger.info("** Discovery already exists, not adding " + type + ": " + discovery);
        }
    }

    // this is used for possibly changing the heading of the drone 
    public Action getCurrAction(){
        return this.currAction; 
    }   
    
    // facingDirection is for the front Nose of the drone, need to know which way FlyForward will take the drone 
    // since other direcitons of the drone can be echoed
    public Direction getFacingDirection(){
        return this.currDir; 
    }
    // setter method for the nose of the drone 
    public void setFacingDirection(Direction currDir){
        this.currDir = currDir; 
    }
    
    // Integer return type to avoid primitive obsession 
    public Integer getBattery(){
        return this.currBattery; 
    }

    public void setBattery(Integer cost) {
        this.currBattery -= cost; 
    }

    // conditional check to see if the drone needs to head home or stop safely w/o loss of signal
    public boolean isBatteryLow() {
        return this.currBattery < this.threshhold;
    }

    // allows drone to move forward
    public String fly(){
        this.decision.put("action","fly"); 
        return decision.toString(); 
    }

    // changes the heading of the drone given a new direction 
    public String heading(Direction changeDirection){
        this.currDir = changeDirection;
        this.decision = new JSONObject();
        this.parameters = new JSONObject();
        
        this.decision.put("action", "heading");
        this.parameters.put("direction", changeDirection);
        this.decision.put("parameters", this.parameters);

        return decision.toString();
    }

    // drone action to use echo to get the range of the nearest island
    public String echo(Direction echoDirection){
        this.echoDirection = echoDirection; 
        
        this.decision = new JSONObject();
        this.parameters = new JSONObject();
        this.decision.put("action","echo");
        this.parameters.put("direction", this.echoDirection); 
        this.decision.put("parameters", this.parameters); 

        return this.decision.toString(); 
    }

    // drone action to see what the biome type is under its current location
    public String scan(){
        this.decision.put("action","scan");
        return this.decision.toString(); 
    }

    // drone goes home ( simulation ends )
    public String stop() {
        this.decision.put("action","stop");
        return this.decision.toString(); 
    }

    // setter and methods used to check if doing a certain action with the drone causes it to lose signal 
    public void setStatus(String status){
        this.status = status; 
    }

    public String getStatus(){
        return status; 
    }

    // updates the attributes of the dorne that need to be tracked 
    public void updateDrone(Integer cost, String status){
        setBattery(cost);  
        setStatus(status); 
    }

    // following methods are quite self-explanatory.


    public void setPrevHorizontalDirection(Direction dir) {
        this.prevHorizontalDirection = dir;
    }

    public Direction getPrevHorizontalDirection() {
        return this.prevHorizontalDirection;
    }

    public void incrementSequentialOutOfRange() {
        this.sequentialOutOfRange++;
    }

    public int getSequentialOutOfRange() {
        return this.sequentialOutOfRange;
    }

    public void resetSequentialOutOfRange() {
        this.sequentialOutOfRange = 0;
    }

}
