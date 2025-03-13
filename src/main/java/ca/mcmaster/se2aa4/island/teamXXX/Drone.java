package ca.mcmaster.se2aa4.island.teamXXX; 


public class Drone { 
    
    private Direction currDir; 
    private BatteryStatus currBattery; 

    private creekArray; 
    private siteArray; 

    private Action currAction; 
    

    public Drone(Direction currDir, BatteryStatus currBattery){

        self.currDir = new Direction(currDir); 
        self.currBattery = new BatteryStatus (currBattery); 


        self.currAction = new Action(); 
    
    }

    public Direction getHeading(){
        return self.currDir; 
    }

    public BatteryStatus getBattery(){
        return self.currBattery; 
    }

    public BatteryStatus setBattery(Integer cost){
        self.currBattery -= cost; 
    }

    private void setCreeks(JSONArray creeks){
        self.creekArray = creeks; 
    }

    public boolean getCreek() {
       if ( self.creeks != null && self.creeks.length() > 0){
        return true; 
       }
       else{
        return false; 
       }
    }
    private void setSites(JSONArray sites){
        self.siteArray = sites; 
    }

    public boolean getSites() {
        if (self.sites != null && self.sites.length() > 0) {
            return true; 
        }
        else{return false;}
    }
    
    public void fly(){
        self.currAction.fly(); 
    }

    public void heading(){
        self.currActoin.heading(); 
    }

    public void echo(){
        self.currAction.echo(); 
    }

    public void scan(){
        self.currAction.scan(); 
    }

    public void stop() {
        self.currAction.stop(); 
    }

}