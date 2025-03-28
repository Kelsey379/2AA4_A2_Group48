package ca.mcmaster.se2aa4.island.teamXXX;

import java.io.StringReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;

import ca.mcmaster.se2aa4.island.teamXXX.enums.Direction;
import eu.ace_design.island.bot.IExplorerRaid;


public class Explorer implements IExplorerRaid {
    private Drone drone; 
    private Island island; 
    private Action currAction; 
    private Direction startDir;


    public  StateMachine stateMachine;
    public MissionControl missionControl;

    public Algorithm algorithm; 

    private final Logger logger = LogManager.getLogger();

    @Override
    public void initialize(String s) {



        logger.info("** Initializing the Exploration Command Center");
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Initialization info:\n {}",info.toString(2));

        String direction = info.getString("heading"); 
        this.startDir = Direction.setStartDir(direction); 
       
        Integer batteryLevel = info.getInt("budget");


        logger.info("The drone is facing {}", direction);
        logger.info("Battery level is {}", batteryLevel);

        // initializes all the objects that are needed for the simulation of searching the island 
        this.drone = new Drone(this.startDir, batteryLevel);
        this.currAction = this.drone.getCurrAction(); 
        this.island = new Island(); 

        this.missionControl = new MissionControl(); 
        this.stateMachine = new StateMachine(this.drone, this.currAction, this.island, this.startDir, this.missionControl); 

        
        
        this.algorithm = new Algorithm(this.drone, this.currAction, this.island, this.startDir, this.missionControl, this.stateMachine); 


    }

    @Override
    public String takeDecision() {

    

        algorithm.runStep();
        String decision = missionControl.getTakeDecision(); 

        logger.info("** Decision: {}",decision);



        

        return decision; 

      
    }

    @Override
    public void acknowledgeResults(String s) {
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        

        missionControl.setResponse(response); 
        
        logger.info("** Response received:\n"+response.toString(2));
        Integer cost = response.getInt("cost");
        logger.info("The cost of the action was {}", cost);
        String status = response.getString("status");
        logger.info("The status of the drone is {}", status);
        JSONObject extraInfo = response.getJSONObject("extras");
        logger.info("Additional information received: {}", extraInfo);

        
    }

    @Override
    public String deliverFinalReport() {
        return "no creek found";
    }

}