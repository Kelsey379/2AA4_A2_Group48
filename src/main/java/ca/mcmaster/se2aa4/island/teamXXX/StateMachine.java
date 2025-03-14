package ca.mcmaster.se2aa4.island.teamXXX;

public class StateMachine {
    public static void main(String[] args) {
        State state = new State();
        
        for (int i = 0; i < 5; i++) {
            state.showState();
            state.changeState();
        }
    }
}
