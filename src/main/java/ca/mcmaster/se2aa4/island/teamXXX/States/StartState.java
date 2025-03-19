package ca.mcmaster.se2aa4.island.teamXXX.States;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.teamXXX.Action; 
import ca.mcmaster.se2aa4.island.teamXXX.Drone;
import ca.mcmaster.se2aa4.island.teamXXX.Island;
import ca.mcmaster.se2aa4.island.teamXXX.MissionControl;
import ca.mcmaster.se2aa4.island.teamXXX.StateMachine;



public class StartState extends State {

    private final Logger logger = LogManager.getLogger(); 

    public StartState(Drone drone, Action action, Island island, StateMachine currState, MissionControl missionControl) {
        super(drone, action, island, currState, missionControl);
    }

    @Override
    public void executeState(){
       logger.info("**State Machine is initialized, enter the machine with the start state"); 
        String resultAction = this.drone.fly(); 

        missionControl.takeDecision(resultAction); 
        


    }



    @Override
    public State exitState(){

        JSONObject response = missionControl.getResponse();
        if (response == null) {
        } else {
            int cost = response.getInt("cost");
            String status = response.getString("status");
            drone.updateDrone(cost, status);
            logger.info("** StartState: Received response, cost: {}, status: {}", cost, status);
        }
        logger.info("** StartState: Transitioning to Turn state.");
        stateMachine.setState(stateMachine.Turn);
        return stateMachine.getState(); 
    }

}
