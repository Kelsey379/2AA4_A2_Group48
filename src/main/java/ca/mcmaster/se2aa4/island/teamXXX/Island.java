package ca.mcmaster.se2aa4.island.teamXXX; 

import org.json.JSONArray;


public class Island {

    protected boolean foundCreek; 
    protected boolean foundSites; 
    protected JSONArray creekArray; 
    protected JSONArray siteArray; 
    protected int range;

    // private JSONObject extraInfo; 

    public Island(){

        this.foundCreek = false; 
        this.foundSites = false; 
        this.range = 0;


    }

    public void setCreeks(Boolean creeks){
        this.foundCreek = creeks; 
    }

    public boolean getCreek() { //algorithm calls this
        return this.foundCreek; 
    }

    public void setSites(Boolean sites){

        this.foundSites = sites; 
    }

    public boolean getSites() {

        return this.foundSites; 
    }

    public void updateIsland(Boolean foundCreek, Boolean foundSite){


        // island.updateBiomesArray() ?? 

        setCreeks(foundCreek);
        setSites(foundSite);  

    }

    public int getRange(){
        return this.range;
    }
    
    public void setRange(int range){
        this.range = range;
    }



}