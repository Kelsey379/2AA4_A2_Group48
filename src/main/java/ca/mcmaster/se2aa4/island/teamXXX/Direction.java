package ca.mcmaster.se2aa4.island.teamXXX;


public enum Direction {
    NORTH("N"), 
    SOUTH("S"), 
    EAST("E"),
    WEST("W"); 

    private final String currDirection; 

    Direction(String startDir) {
        this.currDirection = startDir; 
    }

    public static Directoin setStartDir(String startDir) {
        if (startDir == null){
            throw new IllegalArgumentException("no starting direction");
        }
        switch(startDir.toUpperCase()) {
            case "N" : return NORTH;
            case "S" : return SOUTH; 
            case "E" : return EAST; 
            case "W" : return WEST; 
            default: 
                throw new IllegalArgumentException("Not a valid starting direction")
        }
        
    }
}