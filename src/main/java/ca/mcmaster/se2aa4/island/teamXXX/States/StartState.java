package ca.mcmaster.se2aa4.island.teamXXX.States;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ca.mcmaster.se2aa4.island.teamXXX.Action; 
import ca.mcmaster.se2aa4.island.teamXXX.Drone;
import ca.mcmaster.se2aa4.island.teamXXX.Island;
import ca.mcmaster.se2aa4.island.teamXXX.State;
import ca.mcmaster.se2aa4.island.teamXXX.StateMachine;



public class StartState extends State {

    StateMachine nextState; 

    private final Logger logger = LogManager.getLogger(); 

    public StartState(Drone drone, Action action, Island island) {
        super(drone, action, island);
    }

    @Override
    public String executeState(){
       logger.info("**State Machine is initialized, enter the machine with the start state"); 
        String resultDecision = this.drone.fly(); 

        return resultDecision; 
    }

    @Override
    public State exitState(){
        
       nextState.setState(new FindGround());

       return nextState.getState();
        
    } 

}
