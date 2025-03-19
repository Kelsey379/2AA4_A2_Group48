package ca.mcmaster.se2aa4.island.teamXXX.States;

import org.json.JSONObject;
import ca.mcmaster.se2aa4.island.teamXXX.Action; 
import ca.mcmaster.se2aa4.island.teamXXX.Drone;
import ca.mcmaster.se2aa4.island.teamXXX.Island;
import ca.mcmaster.se2aa4.island.teamXXX.MissionControl;
import ca.mcmaster.se2aa4.island.teamXXX.StateMachine;
import ca.mcmaster.se2aa4.island.teamXXX.enums.Direction;

public class FindGround extends State {

    Direction currDir;
    Boolean lost = false;
    // Flag to indicate that we've issued our initial command
    private boolean startedSearch = false;

    public FindGround(Drone drone, Action action, Island island, StateMachine state, Direction currDir, MissionControl missionControl) {
        super(drone, action, island, state, missionControl);
        this.currDir = currDir; 
    }
    
    @Override
    public void executeState(){
        if (!startedSearch) {
            // Issue the initial echo command.
            String resultAction = drone.echo(Direction.E);
            missionControl.takeDecision(resultAction);
            startedSearch = true;
        } else {
            // If we've already issued a command, simply wait for the response.
        }
    }
    
    @Override
    public State exitState(){
        // Check if a response is available.
        JSONObject response = missionControl.getResponse();
        if (response == null) {
            return stateMachine.getState();
        }
        
        // Process the response.
        Integer cost = response.getInt("cost"); 
        String status = response.getString("status"); 
        JSONObject extras = response.getJSONObject("extras"); 
        Integer range = extras.getInt("range");

        drone.updateDrone(cost, status);
     

        // Decide next command based on range.
        if (range == 0) {
            // Nothing detected: issue fly command.
            missionControl.takeDecision(drone.fly());
        } else {
            // Ground found: adjust heading.
            currDir = Direction.E;
            missionControl.takeDecision(drone.heading(currDir));
        }

        // Determine the next state.
        if(lost) {
            stateMachine.setState(stateMachine.LossOfSignal);
        } else {
            stateMachine.setState(stateMachine.Scan);
        }
        
        // Reset for next cycle.
        startedSearch = false;
        missionControl.setResponse(null);
        return stateMachine.getState();
    }
}
