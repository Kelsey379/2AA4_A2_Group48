package ca.mcmaster.se2aa4.island.teamXXX; 


public class Drone { 
    
    protected Direction currDir; // enum class : currDir.NORTH

    protected Integer currBattery;

    private Action currAction; 
    

    public Drone(Direction startDir, Integer startBattery){

        this.currDir = startDir; 

        this.currBattery = startBattery; 

        this.currAction = new Action(); 
    
    }

    public Direction getHeading(){
        return self.currDir; 
    }

    public Integer getBattery(){
        return self.currBattery; 
    }

    public void setBattery(Integer cost) {
        this.currBattery -= cost; 
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