package ca.mcmaster.se2aa4.island.teamXXX;

public interface Movable {
    FindNewDirection navigate = new FindNewDirection();
    int[] move = navigate.getForward(currHeading)
}