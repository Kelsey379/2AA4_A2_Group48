package ca.mcmaster.se2aa4.island.teamXXX.States;


import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.teamXXX.*;
import ca.mcmaster.se2aa4.island.teamXXX.StateMachine;

public class FlyForward extends State {
     
    private boolean lost = false; 

    public FlyForward(Drone drone, Action action, Island island, StateMachine stateMachine, MissionControl missionControl){
        
        super(drone, action, island, stateMachine, missionControl); 

    }

    @Override 
    public void executeState() {
        String resultAction = drone.fly(); 

        missionControl.takeDecision(resultAction); 

        JSONObject response = missionControl.getResponse(); 


        Integer cost = response.getInt("cost"); 
        String status = response.getString("status"); 

        drone.updateDrone(cost, status);

        if(!status.equals("OK")){
            lost = false; 
        }

    }

    @Override
    public State exitState(){
        if (lost){stateMachine.setState(stateMachine.LossOfSignal);}
        else{stateMachine.setState(stateMachine.Scan); }

        return stateMachine.getState(); 
    }
    
}
