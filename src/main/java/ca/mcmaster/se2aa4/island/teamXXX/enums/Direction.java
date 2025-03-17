package ca.mcmaster.se2aa4.island.teamXXX.enums;

public enum Direction {
    N("N"), 
    S("S"), 
    E("E"),
    
    W("W"); 

    private final String currDirection; 

    Direction(String startDir) {
        this.currDirection = startDir; 
    }

    public static Direction setStartDir(String startDir) {
        if (startDir == null){
            throw new IllegalArgumentException("no starting direction");
        }
        switch(startDir.toUpperCase()) {
            case "N" -> {
                return N;
            }
            case "S" -> {
                return S;
            }
            case "E" -> {
                return E;
            }
            case "W" -> {
                return W;
            }
            default -> throw new IllegalArgumentException("Not a valid starting direction");
        }
        // Direction."N", Direction.N there is a diff
                
    }

    // public String getDir(){
    //     return this.currDirection; 
    // }

    public String getDir(){
        return this.currDirection; 
    }



}