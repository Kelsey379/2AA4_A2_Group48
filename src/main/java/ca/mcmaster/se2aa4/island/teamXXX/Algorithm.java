package ca.mcmaster.se2aa4.island.teamXXX; 

import ca.mcmaster.se2aa4.island.teamXXX.States.*;
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

    private Object Turn; 
    private Object FlyForward; 

    private Object Scan; 



    public Algorithm(Drone drone, Action actoin, Island island, StateMachine stateMachine, Direction startDir){
        this.drone = drone; 
        this.island = island; 
        this.stateMachine = stateMachine;
        
        this.currDir = startDir; 
    }


    public void RUN () { 

        currState = stateMachine.getState(); // startstate

        if(currState.equals(StartState)) {
            // todo- check battery condition?  
            currState.executeState(); 
            currState = currState.exitState(); // currState = findGround 
        }

        if(currState.equals(FindGround)){
            currState.executeState(); 
            
            currState = currState.exitState(); // fly forward or LOS 

            if(currState.equals(LossOfSignal)){
                currState.executeState(); //drone.stop(); 
                currState = currState.exitState(); // currState = null;
            } 
            else if (currState.equals(Turn)){
                currState.executeState();
                currState = currState.exitState(); // change heading to S. 
            }

            else if (currState.equals(FlyForward)){

                int range = island.getNearestRange(); 

                while (range > 0 ) {
                    currState.executeState();
                    
                    range --; 
                }
    
            }
        }

        if (currState.equals(FlyForward)){
            currState = currState.exitState(); 
            if(currState.equals(Scan)){

            }
            else{
                currState.executeState();
                currState = currState.exitState();
            }
        }

        
    }






    


    
}