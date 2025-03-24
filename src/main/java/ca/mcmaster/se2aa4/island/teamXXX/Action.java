package ca.mcmaster.se2aa4.island.teamXXX; 


import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.teamXXX.enums.Direction;

// this is a class that implement the interface named ActionInterface 

// it controls just changing of the direction so that it can be used by the drone class. 
public class Action implements ActionInterface{

    protected Drone drone; 

    protected Island island; 

    protected JSONObject decision;

    // does not change the heading of the drone but rather the direction ( and this direction can be used for echoing or chaning heading ) 
    @Override 
    public Direction turnLeft(Direction currDir){
        switch(currDir){
            case E -> {
                return Direction.N;
            }
            case W -> {
                return Direction.S;
            }
            case N -> {
                return Direction.W;
            }
            case S -> {
                return Direction.E;
            }
        }
        return currDir;
    }


    // does not change the heading of the drone but rather the direction ( and this direction can be used for echoing or chaning heading ) 
    @Override 
    public Direction turnRight(Direction currDir){
        switch(currDir){
            case E -> {
                return Direction.S;
            }
            case W -> {
                return Direction.N;
            }
            case N -> {
                return Direction.E;
            }
            case S -> {
                return Direction.W;
            }
        }
        return currDir;
    }





}