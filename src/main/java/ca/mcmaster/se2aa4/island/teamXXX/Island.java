package ca.mcmaster.se2aa4.island.teamXXX; 

import org.json.JSONArray;


public class Island {

    protected boolean foundCreek; 
    protected boolean foundSites; 
    protected JSONArray creekArray; 
    protected JSONArray siteArray; 

    public Island(){

        this.foundCreek = false; 
        this.foundSites = false; 

    }

    public void setCreeks(JSONArray creeks){
        this.creekArray = creeks; 
    }

    public boolean getCreek() { //algorithm calls this
       if ( this.creekArray != null && this.creekArray.length() > 0){
        return true; 
       }
       else{
        return false; 
       }
    }

    public void setSites(JSONArray sites){

        this.siteArray = sites; 
    }

    public boolean getSites() {

        if (this.siteArray != null && this.siteArray.length() > 0) {
            return true; 
        }
        else{return false;}
    }


}