package ca.mcmaster.se2aa4.island.teamXXX; 


import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.teamXXX.enums.Direction;

public class Action implements ActionInterface{

    protected Drone drone; 

    protected Island island; 

    protected JSONObject decision;

   

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