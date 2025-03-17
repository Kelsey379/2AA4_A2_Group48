package ca.mcmaster.se2aa4.island.teamXXX;


import java.util.List; 
import java.util.ArrayList; 

import ca.mcmaster.se2aa4.island.teamXXX.States.*;
import ca.mcmaster.se2aa4.island.teamXXX.enums.Direction;

public class StateMachine {
    
    public State currentState;


    public State StartState; 
    public State FindCreek; 
    public State FindGround; 
    public State FindSite; 
    public State FlyForward; 
    public State GoHome; 
    public State LossOfSignal; 
    public State Scan; 


    public Drone drone;
    public Action currAction;
    public Island island;



    public List<State> stateMachineStates = new ArrayList<>(); 

    public Direction currDir; 


        // public List<State> states = new ArrayList<State>();


        public StateMachine(Drone drone, Action action, Island island, Direction currDir) {
            this.drone = drone;
            this.currAction = action;
            this.island = island; 
            this.currDir = currDir; 

            this.StartState = new StartState(this.drone, this.currAction, this.island, this); 
 
            this.FindGround = new FindGround(this.drone, this.currAction, this.island, this,this.currDir); 
        
            this.FlyForward = new FlyForward(this.drone, this.currAction, this.island,this); 
            this.GoHome = new GoHome(this.drone, this.currAction, this.island, this); 
            this.LossOfSignal = new LossOfSignal(this.drone, this.currAction, this.island, this); 
            this.Scan = new Scan(this.drone, this.currAction, this.island, this); 

            this.currentState = this.StartState;  

            // this.currentState = new StartState(this.drone, this.currAction, this.island);
        }

        public void setState(State state) {
            this.currentState = state;
        }

        public State getState(){
            return this.currentState; 
        }

        // /*public void transitionTo(State state) {
        //     this.currentState = state;
        // }*/

        
    }

