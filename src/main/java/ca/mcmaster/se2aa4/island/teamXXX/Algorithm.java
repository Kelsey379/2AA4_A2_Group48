package ca.mcmaster.se2aa4.island.teamXXX; 

import ca.mcmaster.se2aa4.island.teamXXX.States.State;
import ca.mcmaster.se2aa4.island.teamXXX.enums.Direction;


public class Algorithm{

    public Drone drone; 
    public Island island; 
    public StateMachine stateMachine; 
    public State currState; 
    public Direction currDir; 
    private Object StartState;
    private Object FindGround;
    private Object LossOfSignal;
    private Object GoHome; 
    private Object Turn; 
    private Object FlyForward; 
    private Object UTurn; 

    private Object Scan; 



    public Algorithm(Drone drone, Action actoin, Island island, StateMachine stateMachine, Direction startDir){
        this.drone = drone; 
        this.island = island; 
        this.stateMachine = stateMachine;
        
        this.currDir = startDir; 
    }


    public void RUN () { 

        currState = stateMachine.getState(); // startstate

        if(currState == stateMachine.StartState) {
            // todo- check battery condition?  
            currState.executeState(); 
            currState = currState.exitState(); // currState = Turn 
        }

        if(currState == stateMachine.Turn){
            currState.executeState(); 
            
            currState = currState.exitState(); // FindGround

        }

        if(currState == stateMachine.FindGround){
            currState.executeState(); 

            currState = currState.exitState();
            if(currState.equals(LossOfSignal)){
                currState.executeState();
                currState = currState.exitState(); 
            }
        }

        while(currState != stateMachine.GoHome){

        
            if (currState == stateMachine.Scan){
                currState.executeState(); 

                currState = currState.exitState(); 

                if(currState == stateMachine.LossOfSignal){
                    currState.executeState();
                    currState = currState.exitState(); 
                }
            }

            if(currState == stateMachine.UTurn){
                if(currState == stateMachine.FlyForward){
                    currState.executeState();
                    currState = currState.exitState(); 
                    if(currState == stateMachine.LossOfSignal){
                        currState.executeState();
                        currState = currState.exitState(); 
                    }
                }
            }

            if(currState == stateMachine.FlyForward){
                currState.executeState();
                currState = currState.exitState(); 
                if(currState == stateMachine.LossOfSignal){
                    currState.executeState();
                    currState = currState.exitState(); 
                }
            }

        } 
        currState.executeState(); // must be goHome
        currState = currState.exitState(); 
        
    }






    


    
}