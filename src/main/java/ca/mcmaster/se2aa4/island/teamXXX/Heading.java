package ca.mcmaster.se2aa4.island.teamXXX;

import ca.mcmaster.se2aa4.island.teamXXX.enums.Direction;

public class Heading {
    private Direction currentDirection;
    private PointOfInterest position;
    
    public Heading(Direction direction, PointOfInterest position) {
        this.currentDirection = direction;
        this.position = position;
    }
    
    public void flyForward() {

    } 
    public void turnLeft() {
        
    } 
    public void turnRight() {} //add logic
    public PointOfInterest getNewPosition() { 
        return position; 
    }
}