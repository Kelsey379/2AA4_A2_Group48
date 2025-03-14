package ca.mcmaster.se2aa4.island.teamXXX;

public class FlyForward extends State {
    
    private Integer range; 
    public FlyForward(Drone drone, Action action, Island island, Integer range){
        
        super(drone, action, island); 
        this.range = range; 


    }

    @Override 
    public String executeState() {
        while (range > 0) {
            range =- 1 ; 
            return drone.fly(); 
        }
    }
    public void exitState(){

    }
    
}
