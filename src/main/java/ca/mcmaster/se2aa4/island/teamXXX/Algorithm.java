package ca.mcmaster.se2aa4.island.teamXXX; 

import ca.mcmaster.se2aa4.island.teamXXX.States.State;
import ca.mcmaster.se2aa4.island.teamXXX.enums.Direction;


public class Algorithm{

    
    public StateMachine stateMachine; 
    public State currState; 
    public MissionControl missionControl; 
   


    public Algorithm(Drone drone, Action action, Island island, Direction startDir, MissionControl missionControl, StateMachine stateMachine){
        this.stateMachine = stateMachine; 
        this.missionControl = missionControl; 
        
    }

    
    public void runStep() {
        currState = stateMachine.getState();
        if (currState == null) {
            return;
        }

        if (missionControl.getResponse() != null) {
           
            State nextState = currState.exitState();
            stateMachine.setState(nextState);
            missionControl.setResponse(null);
            currState = nextState; 
        }

      
        if (currState != null) {
            currState.executeState();
        }
    }

    


    






    


    
}