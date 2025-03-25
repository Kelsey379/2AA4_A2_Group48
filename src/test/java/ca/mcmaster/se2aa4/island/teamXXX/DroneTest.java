package ca.mcmaster.se2aa4.island.teamXXX;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ca.mcmaster.se2aa4.island.teamXXX.enums.Direction;
import org.junit.jupiter.api.BeforeEach;

class DroneTest {
    //ARRANGE
    private Drone drone;

    @BeforeEach
    void setUp() {
        drone = new Drone(Direction.N, 100);
    }

    @Test
    void testInitialDroneState() { //ASSERT
        assertEquals(Direction.N, drone.getFacingDirection(), "The drone should start facing North");
    }

    @Test
    void testDroneSetHeading() {
        assertEquals(Direction.N, drone.getFacingDirection(), "Start facing North");
        drone.heading(Direction.E); //ACT
        assertEquals(Direction.E, drone.getFacingDirection(), "Drone should have turned to the East"); //ASSERT
    }

    @Test
    void testDroneStatusUpdateOk() {
        drone.setStatus("OK");
        assertEquals("OK", drone.getStatus(), "Drone status should be set to OK");
    }

}

