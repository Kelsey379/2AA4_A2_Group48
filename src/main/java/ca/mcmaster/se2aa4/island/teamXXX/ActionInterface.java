package ca.mcmaster.se2aa4.island.teamXXX; 

import ca.mcmaster.se2aa4.island.teamXXX.enums.Direction;


// an interface of actions that are corrleated to the heading of the drone but not the heading. 
public interface ActionInterface { 


    Direction turnLeft(Direction currDir);
    Direction turnRight(Direction currDir);
    

}