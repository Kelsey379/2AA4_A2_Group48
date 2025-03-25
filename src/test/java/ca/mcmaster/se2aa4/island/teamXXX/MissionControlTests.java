package ca.mcmaster.se2aa4.island.teamXXX;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.json.JSONObject;

class MissionControlTests { //ARRANGE
    private MissionControl missionControl;

    @BeforeEach
    void setUp() {
        missionControl = new MissionControl();
    }

    @Test
    void testTakeDecision() {
        missionControl.takeDecision("MOVE");
        assertEquals("MOVE", missionControl.getTakeDecision(), "MissionControl should manage and deal with the move command");
    }

    @Test
    void testSetAndGetResponse() {
        JSONObject response = new JSONObject(); //ARRANGE
        response.put("cost", 10);
        response.put("status", "OK");

        missionControl.setResponse(response); //ACT
        JSONObject receivedResponse = missionControl.getResponse();

        //check that getting and setting responses work properly
        assertEquals(10, receivedResponse.getInt("cost"), "The cost field should be 10"); //ASSERT
        assertEquals("OK", receivedResponse.getString("status"), "The deone's status should be set to OK");
    }
}

