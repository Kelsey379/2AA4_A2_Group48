package ca.mcmaster.se2aa4.island.teamXXX; 


public class Drone { 
    
    protected Direction currDir; 

    protected Integer currBattery;

    private Action currAction; 
    

    public Drone(Direction startDir, Integer startBattery){

        self.currDir = startDir; 

        self.currBattery = startBattery; 

        self.currAction = new Action(); 
    
    }

    public Direction getHeading(){
        return self.currDir; 
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

    public Integer getBattery(){
        return self.currBattery; 
    }

    public void setBattery(Integer cost) {
        self.currBattery -= cost; 
    }

    
    public void fly(){
        self.currAction.fly(); 
    }

    public void heading(){
        self.currAction.heading(); 
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