package ca.mcmaster.se2aa4.island.teamXXX;

import java.util.EnumMap;
import java.util.Map;

import ca.mcmaster.se2aa4.island.teamXXX.enums.Direction;

public class FindNewDirection {

    private final Map<Direction, Direction> rightDir;
    private final Map<Direction, Direction> leftDir;
    private final Map<Direction, int[]> forwardDir;

    public FindNewDirection() {
        this.rightDir = new EnumMap<>(Direction.class);
        this.rightDir.put(Direction.NORTH, Direction.EAST);
        this.rightDir.put(Direction.EAST, Direction.SOUTH);
        this.rightDir.put(Direction.SOUTH, Direction.WEST);
        this.rightDir.put(Direction.WEST, Direction.NORTH);

        this.leftDir = new EnumMap<>(Direction.class);
        this.leftDir.put(Direction.NORTH, Direction.WEST);
        this.leftDir.put(Direction.WEST, Direction.SOUTH);
        this.leftDir.put(Direction.SOUTH, Direction.EAST);
        this.leftDir.put(Direction.EAST, Direction.NORTH);

        this.forwardDir = new EnumMap<>(Direction.class);
        this.forwardDir.put(Direction.NORTH, new int[]{0, 1});
        this.forwardDir.put(Direction.SOUTH, new int[]{0, -1});
        this.forwardDir.put(Direction.EAST, new int[]{1, 0});
        this.forwardDir.put(Direction.WEST, new int[]{-1, 0});
    }

    public Direction getRight(Direction currHeading) {
        return rightDir.get(currHeading);
    }

    public Direction getLeft(Direction currHeading) {
        return leftDir.get(currHeading);
    }

    public int[] getForward(Direction currHeading) {
        return forwardDir.get(currHeading);
    }
    
}
