package ca.mcmaster.se2aa4.island.teamXXX;

public class State {
    private StateInterface state;

    public State() {
        
        state = new FirstState();
    }

    public void setState(StateInterface state) {
        this.state = state;
    }

    public void changeState() {
        state.change(this);
    }

    public void showState() {
        state.executeState();
    }
}