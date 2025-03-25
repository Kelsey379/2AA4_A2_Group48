package ca.mcmaster.se2aa4.island.teamXXX;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

class IslandTest { //ARRANGE
    private Island island;

    @BeforeEach
    void setUp() {
        island = new Island();
    }

    @Test
    void testInitialIslandState() { //ASSERT
        assertFalse(island.getCreek(), "No creeks should be found");
        assertFalse(island.getSites(), "No sites should be found");
    }

    @Test
    void testSetCreeksAndSites() {
        island.setCreeks(true); //ACT
        island.setSites(true);

        assertTrue(island.getCreek(), "Should have found a creek"); //ASSERT
        assertTrue(island.getSites(), "Should have found a site");
    }

    @Test
    void testUpdateIslandData() {
        island.updateIsland(true, false); //ACT
        assertTrue(island.getCreek(), "Island should be updated with found creek"); //ASSERT
        assertFalse(island.getSites(), "Island should be updated with no sites found");
    }
}
