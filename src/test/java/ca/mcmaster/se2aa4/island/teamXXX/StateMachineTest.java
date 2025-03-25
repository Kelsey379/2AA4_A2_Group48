package ca.mcmaster.se2aa4.island.teamXXX;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import ca.mcmaster.se2aa4.island.teamXXX.States.*;
import ca.mcmaster.se2aa4.island.teamXXX.enums.Direction;


class StateMachineTest {
    private StateMachine stateMachine;

    @BeforeEach
    void setUp() {
        //ARRANGE - Set up before unit test cases
        Drone drone = new Drone(Direction.N, 100);
        Action action = new Action();
        Island island = new Island();
        MissionControl missionControl = new MissionControl();
        stateMachine = new StateMachine(drone, action, island, Direction.N, missionControl);
    }

    @Test
    void testInitialStateNotNull() { //ASSERT
        assertNotNull(stateMachine.getState(), "Initial state should not be null");
    }

    @Test
    void testInitialStateIsTurn() { //ASSERT
        assertInstanceOf(Turn.class, stateMachine.getState(), "Initial state should be Turn");
    }

    @Test
    void testStateTransitionToFlyForward() {
        stateMachine.setState(stateMachine.FlyForward); //ACT
        assertInstanceOf(FlyForward.class, stateMachine.getState(), "State should be FlyForward"); //ASSERT
    }

    @Test
    void testStateTransitionToLossOfSignal() {
        stateMachine.setState(stateMachine.LossOfSignal); //ACT
        assertInstanceOf(LossOfSignal.class, stateMachine.getState(), "State should be LossOfSignal"); //ASSERT
    }

    @Test
    void testExitState() {
        //ARRANGE
        Drone drone = new Drone(Direction.N, 100);
        Island island = new Island();
        Action action = new Action();
        MissionControl missionControl = new MissionControl();
        StateMachine stateMachine = new StateMachine(drone, action, island, Direction.N, missionControl);
        JSONObject response = new JSONObject();
        JSONObject extras = new JSONObject();
        extras.put("range", 0);
        extras.put("found", "OUT_OF_RANGE");
        response.put("cost", 10);
        response.put("extras", extras);
        response.put("status", "OK");

        //ACT
        stateMachine.setState(stateMachine.EchoCheck);
        missionControl.setResponse(response);
        State nextState = stateMachine.getState().exitState();

        //ASSERT
        assertFalse(nextState instanceof EchoCheck, "State should not be the same after exiting");
    }

    @Test
    void testTransitionEchoToUTurn() {
        //ARRANGE
        Drone drone = new Drone(Direction.N, 100);
        Island island = new Island();
        Action action = new Action();
        MissionControl missionControl = new MissionControl();
        StateMachine stateMachine = new StateMachine(drone, action, island, Direction.N, missionControl);
        JSONObject response = new JSONObject();
        JSONObject extras = new JSONObject();
        extras.put("range", 0);
        extras.put("found", "OUT_OF_RANGE");
        response.put("cost", 10);
        response.put("extras", extras);
        response.put("status", "OK");

        //ACT
        stateMachine.setState(stateMachine.EchoCheck);
        missionControl.setResponse(response);
        State nextState = stateMachine.getState().exitState();

        //ASSERT
        assertInstanceOf(UTurn.class, nextState, "Should be UTurn");
    }

    @Test
    void testLossOfSignal() {
        stateMachine.setState(stateMachine.LossOfSignal);
        assertInstanceOf(LossOfSignal.class, stateMachine.getState(), "State should be LossOfSignal");
    }


}