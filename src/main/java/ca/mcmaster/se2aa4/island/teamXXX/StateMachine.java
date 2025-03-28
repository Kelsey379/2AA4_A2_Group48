package ca.mcmaster.se2aa4.island.teamXXX;


import ca.mcmaster.se2aa4.island.teamXXX.States.EchoCheck;
import ca.mcmaster.se2aa4.island.teamXXX.States.FindGround;
import ca.mcmaster.se2aa4.island.teamXXX.States.FlyForward;
import ca.mcmaster.se2aa4.island.teamXXX.States.FoundGroundTurnEast;
import ca.mcmaster.se2aa4.island.teamXXX.States.GoHome;
import ca.mcmaster.se2aa4.island.teamXXX.States.IslandEdge;
import ca.mcmaster.se2aa4.island.teamXXX.States.LossOfSignal;
import ca.mcmaster.se2aa4.island.teamXXX.States.NoGroundFlySouth;
import ca.mcmaster.se2aa4.island.teamXXX.States.Scan;
import ca.mcmaster.se2aa4.island.teamXXX.States.State;
import ca.mcmaster.se2aa4.island.teamXXX.States.Turn;
import ca.mcmaster.se2aa4.island.teamXXX.States.UTurn;
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
    public State EchoCheck;
    public State IslandEdge;



    public State Turn; 
    public State UTurn; 


    public State FoundGroundTurnEast; 
    public State NoGroundFlySouth;     

    public Drone drone;
    public Action currAction;
    public Island island;
    public MissionControl missionControl; 
 

    public Direction currDir; 


            // CONSTRUCTOR: is initialized by the explorer class, and the function of this constructor is to
            // initialize all the states that the state machine can step through and that the statemachine can change its currState


        public StateMachine(Drone drone, Action action, Island island, Direction currDir, MissionControl missionControl) {
            this.drone = drone;
            this.currAction = action;
            this.island = island; 
            this.currDir = currDir; 
            this.missionControl = missionControl; 
 
            this.FindGround = new FindGround(this.drone, this.currAction, this.island, this, this.missionControl); 
        
            this.FlyForward = new FlyForward(this.drone, this.currAction, this.island,this, this.missionControl); 
            this.GoHome = new GoHome(this.drone, this.currAction, this.island, this, this.missionControl); 
            this.LossOfSignal = new LossOfSignal(this.drone, this.currAction, this.island, this, this.missionControl); 
            this.Scan = new Scan(this.drone, this.currAction, this.island, this, this.missionControl); 

            this.Turn = new Turn(this.drone, this.currAction, this.island, this, this.missionControl);

            this.UTurn = new UTurn(this.drone, this.currAction, this.island, this, this.missionControl); 

            this.FoundGroundTurnEast = new FoundGroundTurnEast(this.drone, this.currAction, this.island, this,this.missionControl); 

            this.NoGroundFlySouth = new NoGroundFlySouth(this.drone, this.currAction, this.island, this,this.missionControl); 
            this.EchoCheck = new EchoCheck(this.drone, this.currAction, this.island, this,this.missionControl); 
            this.IslandEdge = new IslandEdge(this.drone, this.currAction, this.island, this,this.missionControl); 


            this.currentState = this.Turn;  

        }

        // able to set the nextState that the algorithm will need to step through
        public void setState(State state) {
            this.currentState = state;
        }

        // return the current state that is algorithm is stepping through of the state machine.l 
        public State getState(){
            return this.currentState; 
        }

     

        
    }

