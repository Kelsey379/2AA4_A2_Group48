package ca.mcmaster.se2aa4.island.teamXXX;


import ca.mcmaster.se2aa4.island.teamXXX.States.FindGround;
import ca.mcmaster.se2aa4.island.teamXXX.States.FlyForward;
import ca.mcmaster.se2aa4.island.teamXXX.States.FoundGroundTurnEast;
import ca.mcmaster.se2aa4.island.teamXXX.States.GoHome;
import ca.mcmaster.se2aa4.island.teamXXX.States.LossOfSignal;
import ca.mcmaster.se2aa4.island.teamXXX.States.NoGroundFlySouth;
import ca.mcmaster.se2aa4.island.teamXXX.States.Scan;
import ca.mcmaster.se2aa4.island.teamXXX.States.StartState;
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



    public State Turn; 
    public State UTurn; 


    public State FoundGroundTurnEast; 
    public State NoGroundFlySouth;     

    public Drone drone;
    public Action currAction;
    public Island island;
    public MissionControl missionControl; 
 

    public Direction currDir; 


        // public List<State> states = new ArrayList<State>();


        public StateMachine(Drone drone, Action action, Island island, Direction currDir, MissionControl missionControl) {
            this.drone = drone;
            this.currAction = action;
            this.island = island; 
            this.currDir = currDir; 
            this.missionControl = missionControl; 

            this.StartState = new StartState(this.drone, this.currAction, this.island, this, this.missionControl); 
 
            this.FindGround = new FindGround(this.drone, this.currAction, this.island, this,this.currDir, this.missionControl); 
        
            this.FlyForward = new FlyForward(this.drone, this.currAction, this.island,this, this.missionControl); 
            this.GoHome = new GoHome(this.drone, this.currAction, this.island, this, this.missionControl); 
            this.LossOfSignal = new LossOfSignal(this.drone, this.currAction, this.island, this, this.missionControl); 
            this.Scan = new Scan(this.drone, this.currAction, this.island, this, this.missionControl); 

            this.Turn = new Turn(this.drone, this.currAction, this.island, this, this.currDir, this.missionControl);

            this.UTurn = new UTurn(this.drone, this.currAction, this.island, this,this.currDir, this.missionControl); 

            this.FoundGroundTurnEast = new FoundGroundTurnEast(this.drone, this.currAction, this.island, this,this.currDir, this.missionControl); 

            this.NoGroundFlySouth = new NoGroundFlySouth(this.drone, this.currAction, this.island, this,this.currDir, this.missionControl); 




            this.currentState = this.Turn;  

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

