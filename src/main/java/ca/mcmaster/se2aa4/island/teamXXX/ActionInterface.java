package ca.mcmaster.se2aa4.island.teamXXX; 

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.teamXXX.enums.Direction;

public interface ActionInterface { 

    void updateDrone(JSONObject response); 
    void updateIsland(JSONObject response); 
    Direction turnLeft(Direction currDir);
    Direction turnRight(Direction currDir);

}