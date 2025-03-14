package ca.mcmaster.se2aa4.island.teamXXX.States;

import ca.mcmaster.se2aa4.island.teamXXX.Action;
import ca.mcmaster.se2aa4.island.teamXXX.Drone;
import ca.mcmaster.se2aa4.island.teamXXX.Island;
import ca.mcmaster.se2aa4.island.teamXXX.State;
import ca.mcmaster.se2aa4.island.teamXXX.StateMachine;
import ca.mcmaster.se2aa4.island.teamXXX.enums.Direction;


public class FindGround extends  State{

    StateMachine nextState;
    Direction currDir;

    public FindGround(Drone drone, Action action, Island island) {
        super(drone, action, island);
    }
    
    @Override
    public String executeState(){
        currDir = drone.getFacingDirection();
        if(Integer.parseInt(drone.echo(currDir)) > 0){
                exitState(Integer.parseInt(drone.echo(currDir)));
            }
        else {
            drone.turnRight(currDir);
        }
        Direction dirToEcho = currDir;
        while (Integer.parseInt(drone.echo(action.turnLeft(dirToEcho))) == 0) { 
            
            drone.fly();
                
                //scan left
                //if nothing, fly forward 1 square
                //echon left
                //if nothing, fly forward again, echo left
                //repeat until found
            }
        }
    }

    @Override
    public State exitState(Integer range){

        nextState = new FlyForward(range); 

    }
    
}
