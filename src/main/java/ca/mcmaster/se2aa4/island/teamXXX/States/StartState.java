package ca.mcmaster.se2aa4.island.teamXXX.States;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.teamXXX.Action; 
import ca.mcmaster.se2aa4.island.teamXXX.Drone;
import ca.mcmaster.se2aa4.island.teamXXX.Island;
import ca.mcmaster.se2aa4.island.teamXXX.State;
import ca.mcmaster.se2aa4.island.teamXXX.StateMachine;



public class StartState extends State {

    private final Logger logger = LogManager.getLogger(); 

    public StartState(Drone drone, Action action, Island island, StateMachine currState) {
        super(drone, action, island, currState);
    }

    @Override
    public void executeState(){
       logger.info("**State Machine is initialized, enter the machine with the start state"); 
        String resultAction = this.drone.fly(); 

        missionControl.takeDecision(resultAction); 
        JSONObject response = missionControl.getResponse(); 

        Integer cost = response.getInt("cost"); 
        String status = response.getString("status"); 

        drone.updateDrone(cost, status);


    }

    @Override
    public State exitState(){

        stateMachine.setState(stateMachine.FindGround);
        return stateMachine.getState(); 
        
    } 

}
