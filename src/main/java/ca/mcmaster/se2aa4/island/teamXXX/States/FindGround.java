package ca.mcmaster.se2aa4.island.teamXXX.States;


import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.teamXXX.Action; 
import ca.mcmaster.se2aa4.island.teamXXX.Drone;
import ca.mcmaster.se2aa4.island.teamXXX.Island;
import ca.mcmaster.se2aa4.island.teamXXX.MissionControl;
import ca.mcmaster.se2aa4.island.teamXXX.StateMachine;
import ca.mcmaster.se2aa4.island.teamXXX.enums.Direction;


public class FindGround extends State {

    Direction currDir;
    Boolean lost = false; 
    Boolean flyForward = false ; 
    Boolean turn = false; 


    public FindGround(Drone drone, Action action, Island island, StateMachine state, Direction currDir, MissionControl missionControl) {
        super(drone, action, island, state, missionControl);
        this.currDir = currDir; 
    }
    
    @Override
    public void executeState(){

        String resultAction = drone.echo(currDir); 
        missionControl.takeDecision(resultAction); 

        JSONObject response = missionControl.getResponse(); 

        Integer cost = response.getInt("cost"); 
        String status = response.getString("status"); 
        
        JSONObject extras = response.getJSONObject("extras"); 
        Integer range = extras.getInt("range");

        drone.updateDrone(cost, status);
        island.setNearestRange(range); 
        // ned to check if echo operation cost brings down battery


        if(status.equals("OK")){
            if (range > 0) {
                flyForward = true; 
            }
            else{
                turn = true; 
            }
        }
        else{
            lost = true; 
        }

        // currDir = drone.getFacingDirection();
        // if(Integer.parseInt(drone.echo(currDir)) > 0){
        //         exitState(Integer.parseInt(drone.echo(currDir)));
        //     }
        // else {
        //     drone.turnRight(currDir);
        // }
        // Direction dirToEcho = currDir;
        // while (Integer.parseInt(drone.echo(action.turnLeft(dirToEcho))) == 0) { 
            
        //     drone.fly();
                
        //         //scan left
        //         //if nothing, fly forward 1 square
        //         //echon left
        //         //if nothing, fly forward again, echo left
        //         //repeat until found
        // }
        
    }

    @Override
    public State exitState(){

        if(lost){stateMachine.setState(stateMachine.LossOfSignal);}
        else if (flyForward) {stateMachine.setState(stateMachine.FlyForward);}
        else if (turn){stateMachine.setState(stateMachine.Turn); }

        return stateMachine.getState(); 

    }
    
} 
