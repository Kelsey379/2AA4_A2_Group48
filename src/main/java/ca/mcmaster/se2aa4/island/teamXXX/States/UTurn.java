
package ca.mcmaster.se2aa4.island.teamXXX.States;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.teamXXX.Action;
import ca.mcmaster.se2aa4.island.teamXXX.Drone;
import ca.mcmaster.se2aa4.island.teamXXX.Island;
import ca.mcmaster.se2aa4.island.teamXXX.MissionControl;
import ca.mcmaster.se2aa4.island.teamXXX.StateMachine;
import ca.mcmaster.se2aa4.island.teamXXX.enums.Direction;


public class UTurn extends State {

    public Direction currDir; 
    public Boolean lost; 
    
    public UTurn(Drone drone, Action action, Island island, StateMachine state, Direction currDir, MissionControl missionControl){
        
        super(drone, action, island, state, missionControl);
        this.currDir = currDir; 
    }

    @Override
    public void executeState(){
        
        if(currDir.equals(Direction.E)){
            currDir = Direction.S;
            String currAction = drone.heading(currDir);
            
            missionControl.takeDecision(currAction);
            JSONObject response = missionControl.getResponse(); 

            Integer cost = response.getInt("cost"); 
            String status = response.getString("status"); 
            
            drone.updateDrone(cost, status);
            if(!status.equals("OK")){
                lost = true; 
            }
            if(!lost){
                currDir = Direction.W; 
                currAction = drone.heading(currDir);
            
                missionControl.takeDecision(currAction);
                response = missionControl.getResponse(); 

                cost = response.getInt("cost"); 
                status = response.getString("status"); 
                
                drone.updateDrone(cost, status);

                if(!status.equals("OK")){
                    lost = true; 
                }   
            }
            


        }
        else if (currDir.equals(Direction.W)){
            currDir = Direction.S;
            String currAction = drone.heading(currDir);
            
            missionControl.takeDecision(currAction);
            JSONObject response = missionControl.getResponse(); 

            Integer cost = response.getInt("cost"); 
            String status = response.getString("status"); 
            


            drone.updateDrone(cost, status);

            if(!status.equals("OK")){
                lost = true; 
            }
            if(!lost){
                currDir = Direction.E; 
                currAction = drone.heading(currDir);
            
                missionControl.takeDecision(currAction);
                response = missionControl.getResponse(); 

                cost = response.getInt("cost"); 
                status = response.getString("status"); 
                
                drone.updateDrone(cost, status);

                if(!status.equals("OK")){
                    lost = true; 
                }
            }


        }


    }

    @Override
    public State exitState(){
        if(lost){stateMachine.setState(stateMachine.LossOfSignal);}
        else{stateMachine.setState(stateMachine.FlyForward);}

        return stateMachine.getState(); 
    }
}
