package ca.mcmaster.se2aa4.island.teamXXX;

public class StateMachine {
    private State currentState;
    private Drone drone;
    private Action action;
    public states <State> states = new ArrayList<State>();

    public StateMachine(Drone drone, Action action) {
        this.drone = drone;
        this.action = action;
        this.currentState = new State(drone, action);
    }

    public void setState(State state) {
        this.currentState = state;
    }

    public void run() {
        this.currentState.run();
    }

    public void transitionTo(State state) {
        this.currentState = state;
    }

    public void executeCurrentState() {
        this.currentState.execute();
    }
    
}
