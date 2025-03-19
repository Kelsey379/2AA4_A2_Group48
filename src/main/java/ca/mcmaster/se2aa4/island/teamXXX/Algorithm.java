package ca.mcmaster.se2aa4.island.teamXXX; 

import ca.mcmaster.se2aa4.island.teamXXX.States.State;
import ca.mcmaster.se2aa4.island.teamXXX.enums.Direction;


public class Algorithm{

    //public Drone drone; 
    //public Island island; 
    public StateMachine stateMachine; 
    public State currState; 
    public MissionControl missionControl; 
    // public Direction currDir; 


    public Algorithm(Drone drone, Action action, Island island, Direction startDir, MissionControl missionControl, StateMachine stateMachine){
        this.stateMachine = stateMachine; 
        this.missionControl = missionControl; 
        
    }

    // WHENEVER AN OBJECT IS INSTATIATED OF A CLASS, IF THERE IS NO CONSTRUCTOR JAVA MAKES ONE IT SELF AND U CAN USE THE OBJECTS METHODS

    public void runStep() {
        currState = stateMachine.getState();
        
        if (currState == null) {
            // Nothing to do, or mission is complete
            return;
        }

        if(missionControl.getResponse() == null) {
            currState.executeState();

            return; 
        }
        
        // Execute one step in the current state.
    
        // Transition to the next state.
        State nextState = currState.exitState();
        stateMachine.setState(nextState);

        missionControl.setResponse(null); 
    }


    // public void RUN () { 

    //     currState = stateMachine.getState(); // startstate

    //     if(currState == stateMachine.StartState) {
    //         // todo- check battery condition?  
    //         currState.executeState(); 
    //         currState = currState.exitState(); // currState = Turn 
    //     }

    //     if(currState == stateMachine.Turn){
    //         currState.executeState(); 
            
    //         currState = currState.exitState(); // FindGround

    //     }

    //     if(currState == stateMachine.FindGround){
    //         currState.executeState(); 

    //         currState = currState.exitState();
    //         if(currState.equals(LossOfSignal)){
    //             currState.executeState();
    //             currState = currState.exitState(); 
    //         }
    //     }

    //     while(currState != stateMachine.GoHome){

        
    //         if (currState == stateMachine.Scan){
    //             currState.executeState(); 

    //             currState = currState.exitState(); 

    //             if(currState == stateMachine.LossOfSignal){
    //                 currState.executeState();
    //                 currState = currState.exitState(); 
    //             }
    //         }

    //         if(currState == stateMachine.UTurn){
    //             if(currState == stateMachine.FlyForward){
    //                 currState.executeState();
    //                 currState = currState.exitState(); 
    //                 if(currState == stateMachine.LossOfSignal){
    //                     currState.executeState();
    //                     currState = currState.exitState(); 
    //                 }
    //             }
    //         }

    //         if(currState == stateMachine.FlyForward){
    //             currState.executeState();
    //             currState = currState.exitState(); 
    //             if(currState == stateMachine.LossOfSignal){
    //                 currState.executeState();
    //                 currState = currState.exitState(); 
    //             }
    //         }

    //     } 
    //     currState.executeState(); // must be goHome
    //     currState = currState.exitState(); 
        
    // }






    


    
}