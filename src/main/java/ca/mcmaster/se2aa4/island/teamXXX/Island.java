package ca.mcmaster.se2aa4.island.teamXXX; 

import org.json.JSONArray;

public class Island {

    protected boolean foundCreek; 
    protected boolean foundSites; 
    protected JSONArray creekArray; 
    protected JSONArray siteArray; 
    protected int range;

    private boolean hasLandedOnIsland = false; 

    // Constructor, this class keeps track if sites and creeks have been found or not 
    public Island() {
        this.foundCreek = false; 
        this.foundSites = false; 
        this.range = 0;
    }
    
    // found or not foundc creeks
    public void setCreeks(Boolean creeks) {
        this.foundCreek = creeks; 
    }

    public boolean getCreek() {
        return this.foundCreek; 
    }

    public void setSites(Boolean sites) {
        this.foundSites = sites; 
    }

    public boolean getSites() {
        return this.foundSites; 
    }

    // used to update the attribiutes of the island class, has it found creeks or sites 

    public void updateIsland(Boolean foundCreek, Boolean foundSite) {
       
        if (foundCreek) this.foundCreek = true;
        if (foundSite) this.foundSites = true;
    }
    
    // stores the range of the nearest next island 
    public int getRange() {
        return this.range;
    }
    

    public void setRange(int range) {
        this.range = range;
    }

    //Below methods to track whether or not the drone has landed on the island
    public boolean hasLandedOnIsland() {
        return hasLandedOnIsland;
    }

    public void setHasLandedOnIsland(boolean value) {
        this.hasLandedOnIsland = value;
    }
}
