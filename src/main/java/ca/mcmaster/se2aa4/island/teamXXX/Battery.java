package ca.mcmaster.se2aa4.island.teamXXX;

public class Battery {

    private Integer battery; 

    public Battery(Integer startingBattery) {
        this.battery = startingBattery; 
    }

    public void setBattery(Integer cost) {
        this.battery -= cost;  
    }

    public Integer getBattery() {
        return this.battery;  
    }
}