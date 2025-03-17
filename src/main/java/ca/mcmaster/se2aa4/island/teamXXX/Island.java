package ca.mcmaster.se2aa4.island.teamXXX; 

import org.json.JSONArray;


public class Island {

    protected boolean foundCreek; 
    protected boolean foundSites; 
    protected JSONArray creekArray; 
    protected JSONArray siteArray; 

    // private JSONObject extraInfo; 

    public Island(){

        this.foundCreek = false; 
        this.foundSites = false; 

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


}