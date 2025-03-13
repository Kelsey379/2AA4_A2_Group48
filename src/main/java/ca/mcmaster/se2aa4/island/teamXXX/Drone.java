package ca.mcmaster.se2aa4.island.teamXXX; 


public class Drone { 
    
    private Direction currDir; 
    private BatteryStatus currBattery; 

    private creekArray; 
    private siteArray; 

    private Action currAction; 
    

    public Drone(Direction currDir, BatteryStatus currBattery){

        this.currDir = new Direction(currDir); 
        this.currBattery = new BatteryStatus (currBattery); 


        this.currAction = new Action(); 
    
    }

    public Direction getHeading(){
        return this.currDir; 
    }

    public BatteryStatus getBattery(){
        return this.currBattery; 
    }

    public BatteryStatus setBattery(Integer cost){
        this.currBattery -= cost; 
    }

    private void setCreeks(JSONArray creeks){
        this.creekArray = creeks; 
    }

    public boolean getCreek() {
       if ( this.creeks != null && this.creeks.length() > 0){
        return true; 
       }
       else{
        return false; 
       }
    }
    private void setSites(JSONArray sites){
        this.siteArray = sites; 
    }

    public boolean getSites() {
        if (this.sites != null && this.sites.length() > 0) {
            return true; 
        }
        else{return false;}
    }
    
    public void fly(){
        this.currAction.fly(); 
    }

    public void heading(){
        this.currAction.heading(); 
    }

    public void echo(){
        this.currAction.echo(); 
    }

    public void scan(){
        this.currAction.scan(); 
    }

    public void stop() {
        this.currAction.stop(); 
    }

}